package com.whu.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whu.contract.CertQuery;
import com.whu.contract.UserManager;
import com.whu.contract.VerifyManager;
import com.whu.service.AdminService;
import com.whu.tools.Constant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static com.whu.tools.Tools.createTempFile;


@Service
@com.alibaba.dubbo.config.annotation.Service  //注册到注册中心中
public class AdminServiceImpl implements AdminService {

    Logger logger = Logger.getLogger(this.getClass());
    @Override
    public String create(String password) {
        String fileName = null;
        try {
            fileName = WalletUtils.generateNewWalletFile(
                    password,
                    new File("/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys"), true);
            System.out.println("fileName: "+ fileName);
            //logger.info("fileName", fileName);
        } catch (CipherException | IOException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 部署UserManager和VerifyManager
     * @return
     */
    @Override
    public boolean deployContract() {
        //File tmpFile = null;
        String contractAddress = null;
        try {
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            UserServiceImpl.adminPassWord, UserServiceImpl.adminWalletFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 1000ms
                    UserServiceImpl.web3j, credentials, 7, 20, 1000);
            contractAddress = VerifyManager.deploy(
                    //部署VerifyManager合约
                    UserServiceImpl.web3j,
                    transactionManager,
                    UserServiceImpl.contractGasProvider
            ).send().getContractAddress();
            logger.info("VerifyManager contract deployed to address " + contractAddress);

            Constant.verifyManagerContractAddress = contractAddress;

            contractAddress = UserManager.deploy(
                    //部署UserManager合约
                    UserServiceImpl.web3j,
                    transactionManager,
                    UserServiceImpl.contractGasProvider
            ).send().getContractAddress();
            logger.info("UserManager contract deployed to address " + contractAddress);

            Constant.userManagerContractAddress = contractAddress;
        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
        }*/
        return contractAddress != null;
    }

    @Override
    public String queryByTime(String json, String wallet, String passWord) {
        File tmpFile = null;
        JSONArray res = null;
        try {
            JSONObject queryBody = JSON.parseObject(json);
            String startTime = queryBody.getString("startTime");
            String endTime = queryBody.getString("endTime");
            logger.info("startTime"+ startTime);
            logger.info("endTime"+ endTime);
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            UserServiceImpl.adminPassWord, UserServiceImpl.adminWalletFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 500ms
                    UserServiceImpl.web3j, credentials, 7, 20, 500);
            /*
            //此处应该添加查询管理员的身份信息

            tmpFile = createTempFile(wallet);  //生成临时文件存储钱包
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            passWord, tmpFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 500ms
                    UserServiceImpl.web3j, credentials, 7, 20, 500);
            */
            if ("".equals(Constant.userManagerContractAddress)){
                return null;
            }
            UserManager userManager = UserManager.load(Constant.userManagerContractAddress,
                    UserServiceImpl.web3j, transactionManager, UserServiceImpl.contractGasProvider);
            String conAddressList = userManager.getConAddressList().send();
            JSONArray temp = JSON.parseObject(conAddressList).getJSONArray("contractList");
            //遍历所有的合约地址
            for (int i=0;i<temp.size();i++) {
                CertQuery certQuery = CertQuery.load(temp.getString(i), UserServiceImpl.web3j, transactionManager,
                        UserServiceImpl.contractGasProvider);
                if (i == 0){
                    res = JSON.parseObject(certQuery.search(startTime, endTime).send()).getJSONArray("data");
                }
                //添加所有符合条件的用户位置
                res.addAll(JSON.parseObject(certQuery.search(startTime, endTime).send()).getJSONArray("data"));
            }

            System.out.println("res : " + res);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
        }
        return res!=null?res.toJSONString() : null;
    }

    @Override
    public String queryBySpace(String json, String wallet, String passWord) {
        File tmpFile = null;
        JSONArray res = null;
        try {
            JSONObject queryBody = JSON.parseObject(json);
            String adcode = queryBody.getIntValue("adcode") + "";

            logger.info("adcode"+ adcode);
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            UserServiceImpl.adminPassWord, UserServiceImpl.adminWalletFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 1000ms
                    UserServiceImpl.web3j, credentials, 7, 20, 1000);
            /*
            //此处应该添加查询管理员的身份信息

            tmpFile = createTempFile(wallet);  //生成临时文件存储钱包
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            passWord, tmpFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 500ms
                    UserServiceImpl.web3j, credentials, 7, 20, 500);
            */
            if ("".equals(Constant.userManagerContractAddress)){
                return null;
            }
            UserManager userManager = UserManager.load(Constant.userManagerContractAddress,
                    UserServiceImpl.web3j, transactionManager, UserServiceImpl.contractGasProvider);
            String conAddressList = userManager.getConAddressList().send();
            JSONArray temp = JSON.parseObject(conAddressList).getJSONArray("contractList");
            //遍历所有的合约地址
            for (int i=0;i<temp.size();i++) {
                CertQuery certQuery = CertQuery.load(temp.getString(i), UserServiceImpl.web3j, transactionManager,
                        UserServiceImpl.contractGasProvider);
                if (i == 0){
                    res = JSON.parseObject(certQuery.searchByLocCode(adcode).send()).getJSONArray("data");
                }
                //添加所有符合条件的用户位置
                res.addAll(JSON.parseObject(certQuery.searchByLocCode(adcode).send()).getJSONArray("data"));
            }

            System.out.println("res : " + res);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
        }
        return res!=null?res.toJSONString() : null;
    }
//    UserServiceImpl user = new UserServiceImpl();
//
//    @Override
//    public String verify(JSONObject pol, String wallet, String passWord) {
//        return user.verify(pol, wallet, passWord);
//    }
//
//
//    /**
//     *
//     * @param _json
//     * @return
//     */
//    @Override
//    public String search(String _json, String wallet, String passWord) {
//        return user.search(_json, wallet, passWord);
//    }
//
//    @Override
//    public String create(String passWord) {
//        String fileName = null;
//        try {
//            fileName = WalletUtils.generateNewWalletFile(
//                    passWord,
//                    new File("/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys"), true);
//            System.out.println("fileName: "+ fileName);
//            //logger.info("fileName", fileName);
//        } catch (CipherException | IOException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
//            e.printStackTrace();
//        }
//        return fileName;
//    }
//
//    @Override
//    public String genProof(String position) {
//        return NativeLib.genProof(position);
//    }
//
//
//    @Override
//    public String deployContract(String wallet, String passWord) {
//        return user.deployContract(wallet, passWord);
//    }

}
