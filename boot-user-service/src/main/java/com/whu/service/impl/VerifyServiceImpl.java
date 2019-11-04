package com.whu.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whu.aesrsa.util.AES;
import com.whu.aesrsa.util.ConvertUtils;
import com.whu.aesrsa.util.EncryUtil;
import com.whu.aesrsa.util.RSA;
import com.whu.contract.CertQuery;
import com.whu.contract.VerifyManager;
import com.whu.jni.NativeLib;
import com.whu.service.VerifyService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.dubbo.config.annotation.Reference;

@Service
@com.alibaba.dubbo.config.annotation.Service  //注册到注册中心中
public class VerifyServiceImpl implements VerifyService {

    final static String contractAddress = "0x46e877723e687e4d0764c01632e239cbe0d63fca";
    final static String provideAddr  = "http://202.114.114.47:6789";

    public static final String serverPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALIZ98KqgLW8IMt4" +
            "G+N+4d3DiOiEa+5s6lCMSGE/NbU9stJEqw0EuCP54MY6JkT0HCYTCrLXqww6rSQy" +
            "WF7BNCVGssk2XDcvSKiCz1ZMgabd6XVK5kvIycySydXQ0Ky6rnfxw8w2mllHABFv" +
            "s1eamaHQozv18n/XGqemjW2BFy/jAgMBAAECgYAxT3FCi3SBXKnzy7hk/z9H6Bhi" +
            "0C8V3z/stzpe+mJDYOa+wtZdD15wT4HFQFpSIwgcHo+Kvp2UEDbZ27qN2Y43AZbF" +
            "9LOalWTRUzYtr8wL8MIbgtew/QQ9YFNWdkTZ6MxCItjD/mSz3Lrkcphvbsx4VoCV" +
            "YIJ04r+Loi0t9g0guQJBANvkpfrq0bLVRYWfaigjkx47mr0trJkB7mjADe69Iqts" +
            "M/2x5dHPpClDK78yzAWxU2BrYzOd31QIOm32iMIvRxUCQQDPWJPMOzcq8Jqs1PAM" +
            "7D0hxnvF3tSJB0CJCQWdGFkJiuIYSbrWnCVF78jJyU2AK1H3RDi9BzGPL2Z3i2Si" +
            "+9kXAkAPnKtAJl3fEY9PDmNuGCCA3AB/f/eqIV345/HVSm5kt1j1oSTNAa4JE/DO" +
            "MWAU42MlDFrNtl69y5vCZOeOyeaFAkBOJieGmWcAozDZJWTYqg2cdk/eU08t2nLj" +
            "c2gPPscIRrVSzC9EhhOyWV8HVv0D6s/471inPlfajNYFBp/Goj+/AkEAiejHX/58" +
            "Vv8+ccW22RMZmyxiHcZpTw9hz7vHUCWv03+fyVGtGMhJ4xuPt8UaZm91yHSPWWar" +
            "M8Xa7errKaXN9A==";
    public static final String serverPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyGffCqoC1vCDLeBvjfuHdw4jo" +
            "hGvubOpQjEhhPzW1PbLSRKsNBLgj+eDGOiZE9BwmEwqy16sMOq0kMlhewTQlRrLJ" +
            "Nlw3L0iogs9WTIGm3el1SuZLyMnMksnV0NCsuq538cPMNppZRwARb7NXmpmh0KM7" +
            "9fJ/1xqnpo1tgRcv4wIDAQAB";

    private static final String walletFile = "/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys/95e71e12-f413-1161-45a4-cd0a2dbe0a0f.json";
    private static final String passWord = "12345678";

    private Logger logger = Logger.getLogger(this.getClass());
    private static Web3j web3j = Web3j.build(new HttpService(provideAddr));
    private static ContractGasProvider contractGasProvider = new DefaultGasProvider();

    @Override
    public String verify(JSONObject pol, String wallet, String passWord) {
        String res = null;
        Map<String, Object> params = new HashMap<String,Object>();
        File tmpFile = null;
        try {
            /* logger.info("PoL : " + pol); */
            logger.info("Connected to Ethereum client version: "
                    + web3j.web3ClientVersion().send().getWeb3ClientVersion());   // 得到web3客户端的版本
            //以太坊客户端连接到的网络的链ID   -- 初始设置为7
//            String netVersion = web3j.netVersion().send().getNetVersion();
//            logger.info("netVersion: ", netVersion);

            String clientPublicKey = pol.getString("publicKey");
            String data = pol.getString("data");
            String encryptkey = pol.getString("encryptkey");

            boolean passSign = EncryUtil.checkDecryptAndSign(data,
                    encryptkey, clientPublicKey, serverPrivateKey);
            logger.info("clientPublicKey : " + clientPublicKey);
            if (passSign) {
                // 验签通过
                String aeskey = RSA.decrypt(encryptkey,
                        serverPrivateKey);
                String data2 = ConvertUtils.hexStringToString(AES.decryptFromBase64(data,
                        aeskey));
                //解密得到证书数据并转码
                System.out.println(data2);
                JSONObject jsonObj = JSONObject.parseObject(data2);

                String temp = jsonObj.getString("Prover");

                JSONObject prover = JSON.parseObject(temp);
                JSONArray location = prover.getJSONArray("location");

                JSONArray zeroKnowledgeProofs  = prover.getJSONArray("ZeroKnowledgeProofs");
                logger.info("zeroKnowledgeProofs : " + zeroKnowledgeProofs);
                JSONArray array = new JSONArray();
                JSONArray witnesses = new JSONArray();

                /* ZeroKnowledgeProofs.stream().forEach(jsonobejct->logger.info("proof : ",jsonobejct)); */

                //获取零知识证明中的proof和witness
                for (int i=0;i<zeroKnowledgeProofs.size();i++) {
                    JSONObject tmp = zeroKnowledgeProofs.getJSONObject(i);
                    System.out.println(tmp.toJSONString());
                    /* logger.info("tmp: ", tmp.toJSONString()); */
                    array.add(tmp.getJSONObject("proof"));
                    witnesses.add(tmp.getString("Witness"));
                }
                System.out.println("array : "+array.toJSONString());
                System.out.println("witnesses : "+witnesses.toJSONString());
                //生成临时文件存储钱包
                tmpFile = createTempFile(wallet);
                Credentials credentials =
                        WalletUtils.loadCredentials(
                                passWord, tmpFile);
                logger.info("Credentials loaded");
                TransactionManager transactionManager = new RawTransactionManager(
                        //尝试轮询0x20次，interval is 1000ms
                        web3j, credentials, 7, 20, 1000);
                VerifyManager verifyManager = VerifyManager.load(contractAddress, web3j, transactionManager, contractGasProvider);
                String result = verifyManager.verify(array.toJSONString()).send();

                System.out.println("result : "+result);

                if(!"true".equals(result)){
                    logger.info("verify ZKP failed!");
                }
                else{
                    logger.info("verify ZKP success!");
                    String conAddress = prover.getString("contractAddress");


                    CertQuery certQuery = CertQuery.load(conAddress, web3j, transactionManager,contractGasProvider);
                    logger.info(""+certQuery.isValid());
//                    if(certQuery.isValid()){
                    JSONObject cert = new JSONObject();
                    cert.put("id", pol.getString("id"));
                    cert.put("type", pol.getString("type"));
                    cert.put("prover", prover.getString("Prover"));
                    cert.put("issuanceDate", pol.getString("issuanceDate"));
                    cert.put("location", location);
                    cert.put("witnesses", witnesses);
                    System.out.println("certificate : " + cert.toJSONString());
                    //test delete send() , what will be happend
                    certQuery.addCertificate(cert.toJSONString(), pol.getString("issuanceDate")).send();
                    res = cert.toJSONString();

                }

            } else {
                logger.error("验签失败");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
        }
        return res;
    }
    @Override
    public String search(String _json) {
        JSONObject jsonObject = JSON.parseObject(_json);
        String clientPublicKey = jsonObject.getString("publicKey");
        String data = jsonObject.getString("data");
        String encryptkey = jsonObject.getString("encryptkey");
        String res = null;
        File tmpFile = null;
        try {
            boolean passSign = EncryUtil.checkDecryptAndSign(data,
                    encryptkey, clientPublicKey, serverPrivateKey);
            if (passSign) {
                // 验签通过
                String aeskey = RSA.decrypt(encryptkey,
                        serverPrivateKey);
                String data2 = ConvertUtils.hexStringToString(AES.decryptFromBase64(data,
                        aeskey));
                JSONObject jsonObj = JSONObject.parseObject(data2);
                String temp = jsonObj.getString("search");
                System.out.println("temp :" + temp);
                JSONObject queryBody = JSON.parseObject(temp);
                String conAddress = queryBody.getString("contractAddress");
                String startTime = queryBody.getString("startTime");
                String endTime = queryBody.getString("endTime");

                Credentials credentials =
                        WalletUtils.loadCredentials(
                                passWord, walletFile);
                logger.info("Credentials loaded");
                CertQuery certQuery = CertQuery.load(conAddress, web3j, credentials, contractGasProvider);
                //if(certQuery.isValid()) {
                System.out.println("startTime :" + startTime);
                System.out.println("endTime :" + endTime);
                res = certQuery.search(startTime, endTime).send();
                System.out.println("res : " + res);


            } else {
                logger.error("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String create(String passWord) {
        String fileName = null;
        try {
            fileName = WalletUtils.generateNewWalletFile(
                    passWord,
                    new File("/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys"), true);
            System.out.println("fileName: "+ fileName);
            //logger.info("fileName", fileName);
        } catch (CipherException | IOException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public String genProof(String position) {
        return NativeLib.genProof(position);
    }

    /**
     * 生成临时文件
     * @param json
     * @return
     */
    private File createTempFile(String json){
        FileOutputStream fos = null;
        //创建临时钱包文件
        File walletFile = null;
        ObjectOutputStream out = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

            String s = sd.format(new Date());
            walletFile = File.createTempFile(s, ".json");
            logger.info("临时文件所在的本地路径：" + walletFile.getCanonicalPath());
            fw = new FileWriter(walletFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭临时文件
            try {
                assert bw != null;
                bw.flush();
                fw.flush();
                bw.close();
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return walletFile;

    }

    @Override
    public String deployContract(String wallet, String passWord) {
        File tmpFile = null;
        String contractAddress = null;
        try {
            tmpFile = createTempFile(wallet);
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            passWord, tmpFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 1000ms
                    web3j, credentials, 7, 20, 1000);
            CertQuery contract = CertQuery.deploy(
                    //部署合约
                    web3j,
                    transactionManager,
                    contractGasProvider
            ).send();
            contractAddress = contract.getContractAddress();
            logger.info("Smart contract deployed to address " + contractAddress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
        }

        return contractAddress;
    }


}
