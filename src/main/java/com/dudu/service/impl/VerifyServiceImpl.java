package com.dudu.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dudu.aesrsa.util.*;
import com.dudu.contract.CertQuery;
import com.dudu.contract.VerifyManager;
import com.dudu.jni.NativeLib;
import com.dudu.service.VerifyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.*;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VerifyServiceImpl implements VerifyService {

    final static String contractAddress = "0x909de1dbc7940c8d93fdc0a1d675726079624abb";
    final static String provideAddr  = "http://202.114.114.46:6789";

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

//    private static final String walletFile = "/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys/826b383b-96be-6651-dd23-619231977052.json";
//    private static final String passWord = "12345678";

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Web3j web3j = Web3j.build(new HttpService(provideAddr));

    @Override
    public String verify(JSONObject pol, String wallet, String passWord) {
        String res = null;
        Map<String, Object> params = new HashMap<String,Object>();
        File tmpFile = null;
        try {
            /* logger.info("PoL : " + pol); */
            logger.info("Connected to Ethereum client version: "
                    + web3j.web3ClientVersion().send().getWeb3ClientVersion());   // 得到web3客户端的版本
            String clientPublicKey = pol.getString("publicKey");
            String data = pol.getString("data");
            String encryptkey = pol.getString("encryptkey");
            logger.info("data : " + data);
            logger.info("encryptkey : " + encryptkey);
//            data = prover.
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

                // Now lets deploy a smart contract
//                logger.info("Deploying smart contract");
//                Future<ExAddDemo> con = ExAddDemo.deploy(
//                        web3j, credentials,
//                        GAS_PRICE, GAS_LIMIT, new BigInteger("121212")
//                        );
//                String conadd = "0x7d71268f73cc2f1d0e195dedb781bd8916321859";
                ContractGasProvider contractGasProvider = new DefaultGasProvider();
//                ExAddDemo contract = ExAddDemo.deploy(
//                        web3j,
//                        credentials,
//                        contractGasProvider,
//                        "test"
//                ).send();
//                ExAddDemo con = ExAddDemo.deploy(web3j, credentials,contractGasProvider).send();
//                ExAddDemo contract = ExAddDemo.load(conadd, web3j, credentials,contractGasProvider);
//                String r = contract.setA("1").send();
//                System.out.println("-----------------------------------------r: "+ r);

                VerifyManager verifyManager = VerifyManager.load(contractAddress, web3j, credentials,contractGasProvider);
                String result = verifyManager.verify(array.toJSONString()).send();
                System.out.println("result : "+result);
//                String result = con.getResult().send();
                if(!"true".equals(result)){
                    logger.info("verify ZKP failed!");
                }
                else{
                    logger.info("verify ZKP success!");
                    String conAddress = prover.getString("contractAddress");

                    CertQuery certQuery = CertQuery.load(conAddress, web3j, credentials,contractGasProvider);
//                    BigInteger oldValue = certQuery.sumOfCerts().send();
//                    System.out.println("oldValue: "+oldValue);
                    JSONObject cert = new JSONObject();
                    cert.put("id", pol.getString("id"));
                    cert.put("type", pol.getString("type"));
                    cert.put("prover", prover.getString("Prover"));
                    cert.put("issuanceDate", pol.getString("issuanceDate"));
                    cert.put("location", location);
                    cert.put("witnesses", witnesses);
                    System.out.println("certificate : " + cert.toJSONString());

                    certQuery.addCertificate(cert.toJSONString(), pol.getString("issuanceDate")).send();  //test delete send() , what will be happend
                    res = cert.toJSONString();
                    /*BigInteger newValue = certQuery.sumOfCerts().send();
                    System.out.println("newValue: "+newValue);
                    BigInteger one = new BigInteger("1");
                    if(one.equals(newValue.subtract(oldValue))){
                        res = cert.toJSONString();
                    }*/
                }


//                TransactionReceipt transactionReceipt = con.setA("1").send();
//                TransactionReceipt transactionReceipt1 = con.setB("1").send();
//                TransactionReceipt transactionReceipt2 = con.add().send();
//                String result = con.getA().send();
//                System.out.println("result : "+result);
//                logger.info("result: ", result);

//                logger.info("Smart contract deployed to address " + contractAddress);
//
//                //logger.info("Value stored in remote smart contract: " + contract.verifyZKP(new Utf8String(array.toString()));
//
//                // Lets modify the value in our smart contract
//                contract.verifyZKP(new Utf8String(array.toJSONString()));
//                //根据receipt获取应答
//                //List<RegisterApplyManager.NotifyEventResponse> responses = userManager.getNotifyEvents(transactionReceipt);

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
    public String search(String _json, String wallet, String passWord) {
        JSONObject jsonObject = JSON.parseObject(_json);
        String clientPublicKey = jsonObject.getString("publicKey");
        String data = jsonObject.getString("data");
        String encryptkey = jsonObject.getString("encryptkey");
//        logger.info("data : " + data);
//        logger.info("encryptkey : " + encryptkey);
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
//                System.out.println(data2);
                JSONObject jsonObj = JSONObject.parseObject(data2);
                String temp = jsonObj.getString("search");
                System.out.println("temp :" + temp);
                JSONObject queryBody = JSON.parseObject(temp);
                String conAddress = queryBody.getString("contractAddress");
                String startTime = queryBody.getString("startTime");
                String endTime = queryBody.getString("endTime");

                //生成临时文件存储钱包
                tmpFile = createTempFile(wallet);
                Credentials credentials =
                        WalletUtils.loadCredentials(
                                passWord, tmpFile);
                logger.info("Credentials loaded");

                ContractGasProvider contractGasProvider = new DefaultGasProvider();
                CertQuery certQuery = CertQuery.load(conAddress, web3j, credentials, contractGasProvider);
                System.out.println("startTime :" + startTime);
                System.out.println("endTime :" + endTime);
                res = certQuery.search(startTime, endTime).send();
//                System.out.println("----------------------------------------- ");
                System.out.println("res : " + res);

            } else {
                logger.error("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
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
//            fos = new FileOutputStream(walletFile);
//            out = new ObjectOutputStream(fos);
////            logger.info("wallet: ", json);
//            System.out.println("wallet: "+ json);
//            out.writeObject(JSON.parseObject(json));

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
//                assert out != null;
//                out.flush();
//                out.close();
//                fos.flush();
//                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
//            walletFile.deleteOnExit();//程序退出时删除临时文件
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
            ContractGasProvider contractGasProvider = new DefaultGasProvider();
            CertQuery contract = CertQuery.deploy(
                    //部署合约
                    web3j,
                    credentials,
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
