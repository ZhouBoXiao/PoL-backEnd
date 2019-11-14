package com.whu.service.impl;


import com.whu.service.AdminService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


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
