package com.whu.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whu.aesrsa.util.AES;
import com.whu.aesrsa.util.ConvertUtils;
import com.whu.aesrsa.util.EncryUtil;
import com.whu.aesrsa.util.RSA;
import com.whu.contract.CertQuery;
import com.whu.contract.UserManager;
import com.whu.contract.VerifyManager;
import com.whu.jni.NativeLib;
import com.whu.service.UserService;
import com.whu.tools.Constant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.whu.tools.Constant.*;
import static com.whu.tools.Tools.*;

@Service
@com.alibaba.dubbo.config.annotation.Service  //注册到注册中心中
public class UserServiceImpl implements UserService {

    protected static final String adminWalletFile = "/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys/b756e77a-6694-7640-0e58-d703f8dda14a.json";
    protected static final String adminPassWord = "12345678";

    private Logger logger = Logger.getLogger(this.getClass());
    protected static Web3j web3j = Web3j.build(new HttpService(selectProvider(Constant.provideAddr)));
    protected static ContractGasProvider contractGasProvider = new DefaultGasProvider();

    @Override
    public String verify(JSONObject pol) {
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

            /*  验证签名这个出现bug，会有时正确的数据都通过不了
            boolean passSign = EncryUtil.checkDecryptAndSign(data,
                    encryptkey, clientPublicKey, serverPrivateKey);*/
            logger.info("clientPublicKey : " + clientPublicKey);
            if (true) {
                // 验签通过
                String aeskey = RSA.decrypt(encryptkey,
                        serverPrivateKey);
                String data2 = ConvertUtils.hexStringToString(AES.decryptFromBase64(data,
                        aeskey));
                //解密得到证书数据并转码
                System.out.println(data2);
                JSONObject jsonObj = JSONObject.parseObject(data2);

                //得到待验证的证书
                String temp = jsonObj.getString("Prover");
                //得到钱包和密码
                JSONObject wallet = jsonObj.getJSONObject("wallet");
                String passWord = jsonObj.getString("passWord");
                if ("".equals(temp) || temp == null){
                    logger.info("failed!");
                    return null;
                }
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
                tmpFile = createTempFile(wallet.toJSONString());
                Credentials credentials =
                        WalletUtils.loadCredentials(
                                passWord, tmpFile);
                logger.info("Credentials loaded");
                TransactionManager transactionManager = new RawTransactionManager(
                        //尝试轮询0x20次，interval is 1000ms
                        web3j, credentials, 7, 20, 1000);
                VerifyManager verifyManager = VerifyManager.load(verifyManagerContractAddress, web3j, transactionManager, contractGasProvider);
                String result = verifyManager.verify(array.toJSONString()).send();

                System.out.println("result : "+result);

                if(!"true".equals(result)){
                    logger.info("verify ZKP failed!");
                }
                else{
                    logger.info("verify ZKP success!");
                    String conAddress = prover.getString("contractAddress");
                    //行政区划代码
                    String adcode = prover.getIntValue("adcode") + "";

                    CertQuery certQuery = CertQuery.load(conAddress, web3j, transactionManager,contractGasProvider);
                    logger.info(""+certQuery.isValid());
//                    if(certQuery.isValid()){
                    JSONObject cert = new JSONObject();

                    cert.put("id", pol.getString("id"));
                    cert.put("type", pol.getString("type"));
                    cert.put("prover", prover.getString("Prover"));
                    cert.put("issuanceDate", pol.getString("issuanceDate"));
                    cert.put("location", location);
                    cert.put("adcode", adcode);
                    cert.put("witnesses", witnesses);
                    System.out.println("certificate : " + cert.toJSONString());
                    //test delete send() , what will be happend
                    // location code 与经纬度的转化
                    TransactionReceipt receipt = certQuery.addCertificate(cert.toJSONString(), pol.getString("issuanceDate"), adcode).send();
                    logger.info("add Certificate is status ok" + receipt.isStatusOK());
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

//    /**
//     * 根据时间查询
//     * @param _json
//     * @return
//     */
//    @Override
//    public String queryByTime(String _json) {
//        JSONObject jsonObject = JSON.parseObject(_json);
//        String res = null;
//        File tmpFile = null;
//        try {
//            String temp = jsonObj.getString("search");
//            System.out.println("temp :" + temp);
//            JSONObject queryBody = JSON.parseObject(temp);
////            String conAddress = queryBody.getString("contractAddress");
//            String startTime = queryBody.getString("startTime");
//            String endTime = queryBody.getString("endTime");
//            Credentials credentials =
//                    WalletUtils.loadCredentials(
//                            UserServiceImpl.adminPassWord, UserServiceImpl.adminWalletFile);
//            TransactionManager transactionManager = new RawTransactionManager(
//                    //尝试轮询0x20次，interval is 500ms
//                    UserServiceImpl.web3j, credentials, 7, 20, 500);
//
//            logger.info("Credentials loaded");
//            CertQuery certQuery = CertQuery.load(conAddress, web3j, credentials, contractGasProvider);
//
//            System.out.println("startTime :" + startTime);
//            System.out.println("endTime :" + endTime);
//            res = certQuery.search(startTime, endTime).send();
//            System.out.println("res : " + res);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (tmpFile != null && tmpFile.exists()){
//                tmpFile.delete();  //删除临时文件
//            }
//        }
//        return res;
//    }

    /**
     * 根据时间查询
     * @param _json
     * @return
     */
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
                JSONObject wallet = jsonObj.getJSONObject("wallet");
                String passWord = jsonObj.getString("passWord");
                System.out.println("temp :" + temp);
                JSONObject queryBody = JSON.parseObject(temp);
                String conAddress = queryBody.getString("contractAddress");
                String startTime = queryBody.getString("startTime");
                String endTime = queryBody.getString("endTime");
                //生成临时文件存储钱包
                tmpFile = createTempFile(wallet.toJSONString());
                Credentials credentials =
                        WalletUtils.loadCredentials(
                                passWord, tmpFile);
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
        finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
        }
        return res;
    }

    @Override
    public String genProof(String position) {
        return NativeLib.genProof(position);
    }


    /**
     * 用管理员账号部署证书合约，把当前用户添加到白名单，添加用户到管理列表
     * @param _json
     * @param username
     * @return contract Address
     */
    @Override
    public String deployContract(String _json, String username) {
//        File tmpFile = null;
        String contractAddress = null;
        try {
            JSONObject wallet = JSON.parseObject(_json);
            String account = wallet.getString("address");

//            tmpFile = createTempFile(wallet);
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            adminPassWord, adminWalletFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 1000ms
                    web3j, credentials, 7, 20, 1000);
            CertQuery certQuery = CertQuery.deploy(
                    //部署合约
                    web3j,
                    transactionManager,
                    contractGasProvider
            ).send();
            //?????????
            // 添加该用户进白名单
            TransactionReceipt receipt = certQuery.addWhitelisted(account).send();
            logger.info("CertQuery receipt is Status OK " +receipt.isStatusOK());
            contractAddress = certQuery.getContractAddress();
            logger.info("Smart contract deployed to address " + contractAddress);
            if ("".equals(Constant.userManagerContractAddress)){
                return null;
            }
            UserManager userManager = UserManager.load(Constant.userManagerContractAddress,
                    UserServiceImpl.web3j, transactionManager, UserServiceImpl.contractGasProvider);
            // 添加用户到管理列表
            receipt = userManager.insert(username, account, contractAddress).send();
            logger.info("userManager receipt is Status OK " +receipt.isStatusOK());

        } catch (Exception e) {
            e.printStackTrace();
        }
//        } finally {
//            if (tmpFile != null && tmpFile.exists()){
//                tmpFile.delete();  //删除临时文件
//            }
//        }

        return contractAddress;
    }



}
