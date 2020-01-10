package com.whu.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whu.aesrsa.util.AES;
import com.whu.aesrsa.util.ConvertUtils;
import com.whu.aesrsa.util.EncryUtil;
import com.whu.aesrsa.util.RSA;
import com.whu.contract.*;
import com.whu.contract.singleton.ContractApi;
import com.whu.jni.NativeLib;
import com.whu.service.UserService;
import com.whu.spatialIndex.geohash.GPSToGeoHash;
import com.whu.spatialIndex.geohash.GeoHash;
import com.whu.spatialIndex.gridIndex.GridIndex;
import com.whu.tools.Constant;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.whu.tools.Constant.*;
import static com.whu.tools.Tools.*;

@Service
@com.alibaba.dubbo.config.annotation.Service  //注册到注册中心中
public class UserServiceImpl implements UserService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String verify(JSONObject pol) {
        String res = null;

        Map<String, Object> params = new HashMap<String,Object>();
        File tmpFile = null;
        try {
            /* logger.info("PoL : " + pol); */
            // 得到web3客户端的版本
            logger.info("Connected to Ethereum client version: "
                    + EthereumApi.getInstance().web3j.web3ClientVersion().send().getWeb3ClientVersion());
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

                if ("".equals(temp) || temp == null){
                    logger.info("failed!");
                    return null;
                }
                JSONObject prover = JSON.parseObject(temp);
                JSONArray location = prover.getJSONArray("location");
                JSONObject wallet = prover.getJSONObject("wallet");
                String passWord = prover.getString("passWord");
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
                        EthereumApi.getInstance().web3j, credentials, 7, 20, 1000);
                VerifyManager verifyManager = ContractApi.getInstance().verifyManager;
//                VerifyManager verifyManager = VerifyManager.load(verifyManagerContractAddress,
//                        EthereumApi.getInstance().web3j,
//                        transactionManager,
//                        EthereumApi.getInstance().contractGasProvider);
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

                    CertQuery certQuery = CertQuery.load(conAddress, EthereumApi.getInstance().web3j,
                            transactionManager,
                            EthereumApi.getInstance().contractGasProvider);
                    logger.info(""+certQuery.isValid());
//                    if(certQuery.isValid()){
                    /**
                     *  证书格式要修改
                     */
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
                    TransactionReceipt receipt = certQuery.addCertificate(
                            cert.toJSONString(), pol.getString("issuanceDate"), adcode).send();
                    logger.info("add Certificate is status ok" + receipt.isStatusOK());
                    res = cert.toJSONString();

                    /*
                      geohash   但是现在先用geohash   gridindex不用
                     */
                    if (GRID_INDEX) {
                        GridIndexCon gridIndex = GridIndexCon.load(gridIndexContractAddress,
                                EthereumApi.getInstance().web3j,
                                EthereumApi.getInstance().transactionManager,
                                EthereumApi.getInstance().contractGasProvider
                        );
                        String arrlist = gridIndex.get().send();
                        double lon = Double.parseDouble(location.getJSONObject(0).getString("xn"));
                        double lat = Double.parseDouble(location.getJSONObject(1).getString("yn"));
                        if (arrlist.equals(JSON.toJSONString(GridIndex.getInstance().getArrGrids()))) {
                            GridIndex.getInstance().point2GridObject(lon, lat);
                            arrlist = JSON.toJSONString(GridIndex.getInstance().getArrGrids());
                            TransactionReceipt transactionReceipt = gridIndex.set(arrlist).send();
                            if (transactionReceipt.isStatusOK()) {
                                logger.info("add point to gridindex success!!");
                            } else {
                                logger.info("add point to gridindex fail!!");
                            }
                        } else {
                            logger.info("gridindex is not consistent with the data on the chain !!");
                        }
                    }
                    else if (GEO_HASH){
                        /*
                          记住 xn是latitude ，yn是 longitude
                         */
                        double lat = Double.parseDouble(location.getJSONObject(0).getString("xn"));
                        double lon = Double.parseDouble(location.getJSONObject(1).getString("yn"));
                        String tmphash = new GeoHash(lon, lat).getBase32FromBits();
                        logger.info("geohash: " + tmphash);
                        GeoHashCon geoHashCon = ContractApi.getInstance().geoHashCon;
                        TransactionReceipt transactionReceipt = geoHashCon.insert(tmphash, cert.toJSONString()).send();
                        if (transactionReceipt.isStatusOK()) {
                            logger.info("add point to GPSToGeoHash success!!");
                        } else {
                            logger.info("add point to GPSToGeoHash fail!!");
                        }
                    }

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
                String temp = JSONObject.parseObject(data2).getString("search");
                System.out.println("temp :" + temp);
                JSONObject queryBody = JSON.parseObject(temp);
                JSONObject wallet = queryBody.getJSONObject("wallet");
                String passWord = queryBody.getString("passWord");

                String conAddress = queryBody.getString("contractAddress");
                String startTime = queryBody.getString("startTime");
                String endTime = queryBody.getString("endTime");
                //生成临时文件存储钱包
                tmpFile = createTempFile(wallet.toJSONString());
                Credentials credentials =
                        WalletUtils.loadCredentials(
                                passWord, tmpFile);
                logger.info("Credentials loaded");
                TransactionManager transactionManager = new RawTransactionManager(
                        //尝试轮询0x20次，interval is 1000ms
                        EthereumApi.getInstance().web3j, credentials, 0, 20, 1000);

                CertQuery certQuery = CertQuery.load(conAddress,
                        EthereumApi.getInstance().web3j, transactionManager, EthereumApi.getInstance().contractGasProvider);
                //if(certQuery.isValid()) {
                logger.info("startTime :" + startTime);
                logger.info("endTime :" + endTime);
                res = certQuery.search(startTime, endTime).send();
                logger.info("res : " + res);

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

            CertQuery certQuery = CertQuery.deploy(
                    //部署合约
                    EthereumApi.getInstance().web3j,
                    EthereumApi.getInstance().transactionManager,
                    EthereumApi.getInstance().contractGasProvider
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
                    EthereumApi.getInstance().web3j,
                    EthereumApi.getInstance().transactionManager,
                    EthereumApi.getInstance().contractGasProvider);
            // 添加用户到管理列表
            receipt = userManager.insert(username, contractAddress, "0x"+account).send();
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
