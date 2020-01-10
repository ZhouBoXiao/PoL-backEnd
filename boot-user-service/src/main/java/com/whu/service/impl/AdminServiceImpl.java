package com.whu.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whu.contract.*;
import com.whu.contract.singleton.ContractApi;
import com.whu.service.AdminService;
import com.whu.spatialIndex.geohash.BoundingBox;
import com.whu.spatialIndex.geohash.GeoHashBoundingBoxQuery;
import com.whu.spatialIndex.gridIndex.GridIndex;
import com.whu.spatialIndex.gridIndex.Point;
import com.whu.tools.Constant;

import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

import static com.whu.tools.Constant.geoHashConContractAddress;
import static com.whu.tools.Constant.gridIndexContractAddress;


@Service
@com.alibaba.dubbo.config.annotation.Service  //注册到注册中心中
public class AdminServiceImpl implements AdminService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AdminServiceImpl.class);


    /**
     * 创建账户
     * @param password
     * @return
     */
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
     *
     *
     * @return
     */
    @Override
    public boolean deployContract() {
        //File tmpFile = null;
        String cAddress = null;
        try {


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
                    EthereumApi.getInstance().web3j,
                    EthereumApi.getInstance().transactionManager,
                    contractGasProvider
            ).send();
            cAddress = userManager.getContractAddress();
            logger.info("UserManager contract deployed to address " + cAddress);

            Constant.userManagerContractAddress = cAddress;
//            TransactionReceipt receipt = userManager.insert("admin","4e03a504a63d7ca2ee5c527776bf394438520863", "dfd7a029dcb717624e4cce0e42d47f0e7e68fc00").send();
//
//            logger.info("add Certificate is status ok" + receipt.isStatusOK());
            String contractAddress = VerifyManager.deploy(
                    //部署VerifyManager合约
                    EthereumApi.getInstance().web3j,
                    EthereumApi.getInstance().transactionManager,
                    EthereumApi.getInstance().contractGasProvider
            ).send().getContractAddress();
            logger.info("VerifyManager contract deployed to address " + contractAddress);

            Constant.verifyManagerContractAddress = contractAddress;

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
            logger.info("json:  " + json);
            String startTime = queryBody.getString("startTime");
            String endTime = queryBody.getString("endTime");
            logger.info("startTime"+ startTime);
            logger.info("endTime"+ endTime);
            //使用管理员钱包

            logger.info("userManagerContractAddress:" + Constant.userManagerContractAddress);
            if ("".equals(Constant.userManagerContractAddress)) {
                return null;
            }
            UserManager userManager = ContractApi.getInstance().userManager;
//            UserManager userManager = UserManager.load(Constant.userManagerContractAddress,
//                    EthereumApi.getInstance().web3j, EthereumApi.getInstance().transactionManager, EthereumApi.getInstance().contractGasProvider);
            logger.info("UserManager loaded !!!");
            if (queryBody.containsKey("username")) {

                String username = queryBody.getString("username");
                logger.info("username: " + username);
                String conAddress = userManager.getContractByUsername(username).send();
                logger.info("conAddress: " + conAddress);
                CertQuery certQuery = CertQuery.load(conAddress, EthereumApi.getInstance().web3j, EthereumApi.getInstance().transactionManager,
                        EthereumApi.getInstance().contractGasProvider);
                logger.info("----------------------------------------------------------------------------");
//                logger.info(certQuery.sumOfCerts());
//                logger.info(certQuery.listAll().send());
                result = certQuery.search(startTime, endTime).send();
                logger.info("result: "+ result);
            } else {
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
                    CertQuery certQuery = CertQuery.load(conAddrs[i], EthereumApi.getInstance().web3j, EthereumApi.getInstance().transactionManager,
                            EthereumApi.getInstance().contractGasProvider);
                    if (i == 0) {
                        res = JSON.parseObject(certQuery.search(startTime, endTime).send()).getJSONArray("data");
                    }
                    //添加所有符合条件的用户位置
                    res.addAll(JSON.parseObject(certQuery.search(startTime, endTime).send()).getJSONArray("data"));
                }

                logger.info("res : " + res);
                JSONObject tmp = new JSONObject();
                assert res != null;
                tmp.put("total", res.size());
                tmp.put("data", res);
                result = tmp.toJSONString();

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
            UserManager userManager = ContractApi.getInstance().userManager;
//            UserManager userManager = UserManager.load(Constant.userManagerContractAddress,
//                    EthereumApi.getInstance().web3j, EthereumApi.getInstance().transactionManager, EthereumApi.getInstance().contractGasProvider);
            String conAddressList = userManager.getConAddressList().send();
            String[] conAddrs = conAddressList.split(",");
            /*


             */
//            JSONArray temp = JSON.parseObject(conAddressList).getJSONArray("contractList");
            //遍历所有的合约地址
            for (int i=0;i<conAddrs.length;i++) {
                CertQuery certQuery = CertQuery.load(conAddrs[i], EthereumApi.getInstance().web3j, EthereumApi.getInstance().transactionManager,
                        EthereumApi.getInstance().contractGasProvider);
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

    /**
     * 初始化网格索引
     * 后续改成所有初始化的集合，如加上部署合约和new EthereumApi
     * @return
     */
    @Override
    public boolean initGridIndex() {

        boolean res = false;
        double xStart=113;//X方向上的起始坐标
        double yStart=29;//Y方向上的起始坐标
        double xEnd=116;
        double yEnd=32;
        double dx=0.05;//
        double dy=0.05;//
        try {
            GridIndex.getInstance().createGridIndex(xStart, yStart, xEnd, yEnd, dx, dy);
            String objectToJson = JSON.toJSONString(GridIndex.getInstance().getArrGrids());
            GridIndexCon gridIndexCon = GridIndexCon.load(gridIndexContractAddress,
                    EthereumApi.getInstance().web3j,
                    EthereumApi.getInstance().transactionManager,
                    EthereumApi.getInstance().contractGasProvider
            );
            System.out.println(objectToJson);
//            gridIndexCon.set("1").send();
            gridIndexCon.set(objectToJson).send();
//            TransactionReceipt transactionReceipt = gridIndexCon.set(objectToJson).send();
//            if (transactionReceipt.isStatusOK()) {
//                logger.info("gridindex init success!!");
//            } else {
//                logger.info("gridindex init failed!!");
//            }
//            logger.info(objectToJson);
            res = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }



    @Override
    public String getPointsFromRegion(String json){
        String res = null;
        try {
            JSONObject queryBody = JSON.parseObject(json);
            double xQueryStart = queryBody.getDouble("xQueryStart");
            double yQueryStart = queryBody.getDouble("yQueryStart");
            double xQueryEnd = queryBody.getDouble("xQueryEnd");
            double yQueryEnd = queryBody.getDouble("yQueryEnd");

            logger.info("json" + json);
            GridIndexCon gridIndex = GridIndexCon.load(gridIndexContractAddress,
                    EthereumApi.getInstance().web3j,
                    EthereumApi.getInstance().transactionManager,
                    EthereumApi.getInstance().contractGasProvider
            );
            String arrlist = gridIndex.get().send();

            if (arrlist.equals(JSON.toJSONString(GridIndex.getInstance().getArrGrids()))) {

                List<Point> list = GridIndex.getInstance().getPointsFromRegion(
                        xQueryStart, yQueryStart, xQueryEnd, yQueryEnd);


                JSONObject tmp = new JSONObject();
                assert list != null;
                tmp.put("total", list.size());
                tmp.put("data", list);
                res = tmp.toJSONString();
            } else {
                logger.info("gridindex is not consistent with the data on the chain !!");
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getPointsFromRegionByGeoHash(String json){
        String res = null;
        try {
            JSONObject queryBody = JSON.parseObject(json);
            double minLon = queryBody.getDouble("minLon");
            double minLat = queryBody.getDouble("minLat");
            double maxLon = queryBody.getDouble("maxLon");
            double maxLat = queryBody.getDouble("maxLat");

            GeoHashBoundingBoxQuery boxQuery2=new GeoHashBoundingBoxQuery();
            //选择范围
            BoundingBox box2=new BoundingBox(minLon,minLat,maxLon,maxLat);
            // 得到可能的所有geohash前缀
            ArrayList<String> strlists=boxQuery2.getBoundingGeoHashBase32(box2);

            //获取到geohash合约对象
            GeoHashCon geoHashCon = ContractApi.getInstance().geoHashCon;

            StringBuilder tmp= new StringBuilder();
            //连接所有的geohash前缀，小于4位则把4位都列出来，保证至少是4位geohash前缀
            for(String str:strlists)
            {
                if(str.length() == 3){
                    String tmp1 = geoHashCon.print(str).send();
                    tmp.append(tmp1);
                }
                else if (str.length() == 2){
                    String tmp1="";
                    try {
                        tmp1 = geoHashCon.print(str).send();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    finally {
                        if ("".equals(tmp1)) {
                            continue;
                        }
                    }
                    // 2位找3位
                    String[] tmp2 = tmp1.split(",");
                    tmp1 = "";
                    for (String aTmp2 : tmp2) {
                        try {
                            tmp1 = geoHashCon.print(aTmp2).send();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }finally {
                            tmp.append(tmp1);
                        }

                    }
//                    Arrays.stream(tmp2).forEach(x -> {
//                        try {
//                            tmp.append(geoHashCon.print(x).send());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
                }
                else if (str.length() == 1){
                    String tmp1="";
                    try {
                        tmp1 = geoHashCon.print(str).send();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }finally {
                        if ("".equals(tmp1)) {
                            continue;
                        }
                    }
                    String[] tmp2 = tmp1.split(",");
                    tmp1 = "";
                    for (String aTmp2 : tmp2) {
                        try {
                            tmp1 = geoHashCon.print(aTmp2).send();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }finally {
                            if ("".equals(tmp1)) {
                                continue;
                            }
                        }
                        String[] tmp4 = tmp1.split(",");
                        for (String aTmp4 : tmp4) {
                            tmp.append(geoHashCon.print(aTmp4).send());
                        }
                    }
                }
                else{
                    tmp.append(str).append(",");
                }
            }
            String[] prefixs = tmp.substring(0, tmp.length()-1).split(",");
            logger.info("json: " + json);
            logger.info("base32list: "+ tmp.toString());

//            GeoHashCon geoHashCon = GeoHashCon.load(geoHashConContractAddress,
//                    EthereumApi.getInstance().web3j,
//                    EthereumApi.getInstance().transactionManager,
//                    EthereumApi.getInstance().contractGasProvider
//            );
            String arrlist;
            JSONObject tmpstr = new JSONObject();
            JSONArray allArray = new JSONArray();
            //遍历所有的前缀
            for(String str:prefixs){
                // 调用合约查询得到结果
                arrlist = geoHashCon.BoundingBoxQuery(str).send();
                if (!"".equals(arrlist)){

                    JSONArray tmparray = JSONArray.parseArray("[" + arrlist.substring(0, arrlist.length()-1) + "]");
                    Map<Object, Object> dateMap = new HashMap<>();
                    double lat,lon;
                    if(tmparray.getJSONObject(0).getString("issuanceDate") != null) {
                        //删除重复数据，根据issuanceDate + prover 来判断
                        for (int i = 0; i < tmparray.size(); i++) {
                            JSONObject object = tmparray.getJSONObject(i);

                            lat = Double.parseDouble(object.getJSONArray("location").getJSONObject(0).getString("xn"));
                            lon = Double.parseDouble(object.getJSONArray("location").getJSONObject(1).getString("yn"));
                            // 筛选出在范围内的坐标点
                            if (lat > maxLat || lat < minLat || lon > maxLon || lon < minLon){
                                tmparray.remove(object);
                                i--;
                                continue;
                            }
                            Object issuanceDate = object.getString("issuanceDate") + object.get("prover");

                            if (dateMap.get(issuanceDate) != null) {
                                // 已存在
                                tmparray.remove(object);
                                i--;

                            } else {
                                dateMap.put(issuanceDate, object);
                            }
                        }
                    }else{

                        //删除重复数据，根据name来判断
                        for (int i = 0; i < tmparray.size(); i++) {
                            JSONObject object = tmparray.getJSONObject(i);

                            lat = Double.parseDouble(object.getJSONArray("location").getJSONObject(0).getString("xn"));
                            lon = Double.parseDouble(object.getJSONArray("location").getJSONObject(1).getString("yn"));
                            if (lat > maxLat || lat < minLat || lon > maxLon || lon < minLon){
                                tmparray.remove(object);
                                i--;
                                continue;
                            }
                            /*
                            Object name = object.getString("name") + object.get("address");

                            if (dateMap.get(name) != null) {
                                // 已存在
                                tmparray.remove(object);

                                i--;

                            } else {
                                dateMap.put(name, object);
                            }*/
                        }
                    }
                    allArray.addAll(tmparray);

                }
            }
            if(allArray.size() > 0) {
                tmpstr.put("total", allArray.size());
                tmpstr.put("data", allArray);
                res = tmpstr.toJSONString();
            }
            else{
                res = "{\"total\": 0, \t\"data\": [] }" ;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }



}
