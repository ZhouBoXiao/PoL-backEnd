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
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
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
        } catch (CipherException | IOException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
                | NoSuchProviderException e) {
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
        String cAddress = null;
        try {
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            UserServiceImpl.adminPassWord, UserServiceImpl.adminWalletFile);
            TransactionManager transactionManager = new RawTransactionManager(
                    //尝试轮询0x20次，interval is 1000ms
                    UserServiceImpl.web3j, credentials, 7, 20, 1000);

            //动态的 gas price and limit
            ContractGasProvider contractGasProvider = new DefaultGasProvider() {
                @Override
                public BigInteger getGasPrice(String contractFunc) {
                    switch (contractFunc) {
                        case UserManager.FUNC_DEPLOY: return BigInteger.valueOf(44_000_000_000L);
                        case UserManager.FUNC_INSERT: return BigInteger.valueOf(44_000_000_000L);
                        case UserManager.FUNC_GETCONADDRESSLIST: return BigInteger.valueOf(22_000_000_000L);
                        case UserManager.FUNC_GETUSERLIST: return BigInteger.valueOf(22_000_000_000L);
                        case UserManager.FUNC_REMOVE: return BigInteger.valueOf(44_000_000_000L);
                        case UserManager.FUNC_GETCONTRACTBYUSERNAME: return BigInteger.valueOf(22_000_000_000L);
//                        case UserManager.FUNC_GETUSERCONTRAC: return BigInteger.valueOf(22_000_000_000L);
                        default: throw new NotImplementedException();
                    }
                }

                @Override
                public BigInteger getGasLimit(String contractFunc) {
                    switch (contractFunc) {
                        case UserManager.FUNC_DEPLOY: return BigInteger.valueOf(8_300_000);
                        case UserManager.FUNC_INSERT: return BigInteger.valueOf(4_300_000);
                        case UserManager.FUNC_GETCONADDRESSLIST: return BigInteger.valueOf(4_300_000);
                        case UserManager.FUNC_GETUSERLIST: return BigInteger.valueOf(4_300_000);
                        case UserManager.FUNC_REMOVE: return BigInteger.valueOf(4_300_000);
                        case UserManager.FUNC_GETCONTRACTBYUSERNAME: return BigInteger.valueOf(4_300_000);
//                        case UserManager.FUNC_GETUSERCONTRACT: return BigInteger.valueOf(4_300_000);
                        // 还有很多case没有写
                        default: throw new NotImplementedException();
                    }
                }
            };
            UserManager userManager = UserManager.deploy(
                    //部署UserManager合约
                    UserServiceImpl.web3j,
                    transactionManager,
                    contractGasProvider
            ).send();
            cAddress = userManager.getContractAddress();
            logger.info("UserManager contract deployed to address " + cAddress);

            Constant.userManagerContractAddress = cAddress;
            TransactionReceipt receipt = userManager.insert("admin","4e03a504a63d7ca2ee5c527776bf394438520863", "dfd7a029dcb717624e4cce0e42d47f0e7e68fc00").send();

            logger.info("add Certificate is status ok" + receipt.isStatusOK());
            /*String contractAddress = VerifyManager.deploy(
                    //部署VerifyManager合约
                    UserServiceImpl.web3j,
                    transactionManager,
                    UserServiceImpl.contractGasProvider
            ).send().getContractAddress();
            logger.info("VerifyManager contract deployed to address " + contractAddress);

            Constant.verifyManagerContractAddress = contractAddress;*/

        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            if (tmpFile != null && tmpFile.exists()){
                tmpFile.delete();  //删除临时文件
            }
        }*/
        return cAddress != null;
    }

    @Override
    public String queryByTime(String json) {
        File tmpFile = null;
        String result = null;

        try {
            JSONArray res = null;
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
            logger.info("userManagerContractAddress:" + Constant.userManagerContractAddress);
            if ("".equals(Constant.userManagerContractAddress)) {
                return null;
            }
            UserManager userManager = UserManager.load(Constant.userManagerContractAddress,
                    UserServiceImpl.web3j, transactionManager, UserServiceImpl.contractGasProvider);
            logger.info("UserManager loaded !!!");
            if (queryBody.containsKey("username")){
                String username = queryBody.getString("username");
                logger.info("username" + username);
                String conAddress = userManager.getContractByUsername(username).send();
                CertQuery certQuery = CertQuery.load(conAddress, UserServiceImpl.web3j, transactionManager,
                        UserServiceImpl.contractGasProvider);
                result = certQuery.search(startTime, endTime).send();
                logger.info("result: "+ result);
            }
            else {
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
                String conAddressList = userManager.getConAddressList().send();
                String[] conAddrs = conAddressList.split(",");
                /*

                 */
                logger.info("conAddressList: " + conAddressList);
//                JSONArray temp = JSON.parseObject(conAddressList).getJSONArray("contractList");
                //遍历所有的合约地址
                for (int i = 0; i < conAddrs.length; i++) {
                    CertQuery certQuery = CertQuery.load(conAddrs[i], UserServiceImpl.web3j, transactionManager,
                            UserServiceImpl.contractGasProvider);
                    if (i == 0) {
                        res = JSON.parseObject(certQuery.search(startTime, endTime).send()).getJSONArray("data");
                    }
                    //添加所有符合条件的用户位置
                    res.addAll(JSON.parseObject(certQuery.search(startTime, endTime).send()).getJSONArray("data"));
                }

                System.out.println("res : " + res);
                result = res.toJSONString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (tmpFile != null && tmpFile.exists()){
//                tmpFile.delete();  //删除临时文件
//            }
//        }
        return result;
    }

    @Override
    public String queryBySpace(String json) {
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
            String[] conAddrs = conAddressList.split(",");
            /*


             */
//            JSONArray temp = JSON.parseObject(conAddressList).getJSONArray("contractList");
            //遍历所有的合约地址
            for (int i=0;i<conAddrs.length;i++) {
                CertQuery certQuery = CertQuery.load(conAddrs[i], UserServiceImpl.web3j, transactionManager,
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
        }
//        finally {
//            if (tmpFile != null && tmpFile.exists()){
//                tmpFile.delete();  //删除临时文件
//            }
//        }
        return res!=null?res.toJSONString() : null;
    }


}
