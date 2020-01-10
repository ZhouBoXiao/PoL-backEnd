package com.whu.contract;

import com.whu.tools.Constant;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;

import static com.whu.tools.Tools.selectProvider;

public class EthereumApi {
    private static final String INFURA_MAIN_NODE = "https://mainnet.infura.io/xxx";
    private static final String INFURA_TEST_NODE = "https://ropsten.infura.io/xxx";

    private static final BigInteger GAS_PRICE = new BigInteger("100000000000");
    private static final BigInteger GAS_LIMIT = new BigInteger("4712388");
    private static final BigInteger INITIAL_WEI = new BigInteger("1");
    private static final String PRIVATE_KEY = "";

    private static final boolean DEV_MODE = true;

//    private volatile static EthereumApi instance;
    public Web3j web3j;
//    private Streak contract = null;
    private String contractAddress = "";
    public ContractGasProvider contractGasProvider;
    public TransactionManager transactionManager;
    private String adminWalletFile;
    private static Admin admin;
    private EthereumApi() {
        if(DEV_MODE) {
            web3j = Web3j.build(new HttpService(selectProvider(Constant.provideAddr)));
            System.out.println("[ETH-INFO] Connected to TestRPC");
        } else {
//            web3j = Web3j.build(new InfuraHttpService(INFURA_TEST_NODE));
            System.out.println("[ETH-INFO] Connected to Infura Node on Ropsten...");
        }
        try {
//            adminWalletFile = "C:\\Users\\Administrator\\keystores\\125308d1-3a4e-ee28-87c6-38ae02b4f6c3.json";
            adminWalletFile = "C:\\Users\\Administrator\\keystores\\admin.json";
//            adminWalletFile = "/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys/125308d1-3a4e-ee28-87c6-38ae02b4f6c3.json";
            String adminPassWord = "12345678";
            Credentials credentials = WalletUtils.loadCredentials(
                    adminPassWord, adminWalletFile);
            transactionManager = new RawTransactionManager(
                    //尝试轮询0x40次，interval is 500ms
                    web3j, credentials, 0, 40, 500);
            System.out.println("Credentials: " + credentials.getAddress());

            contractGasProvider = new DefaultGasProvider();
        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }


    }

//    /**
//     * 双检锁/双重校验锁（DCL，即 double-checked locking）
//     */
//    public static EthereumApi getInstance() {
//        if (instance == null) {
//            synchronized (EthereumApi.class) {
//                if (instance == null) {
//                    instance = new EthereumApi();
//                }
//            }
//        }
//        return instance;
//    }

    /**
     * 静态内部类单例模式
     * @returnb
     */
    public static EthereumApi getInstance(){
        return Inner.instance;
    }



    private static class Inner {
        private static final EthereumApi instance = new EthereumApi();
    }

    /**
     * Delete instance of the API
     */
//    public void close() {
//        instance = null;
//    }

    /**
     * 账号解锁
     */
//    private static void unlockAccount() {
//        String address = "0x05f50cd5a97d9b3fec35df3d0c6c8234e6793bdf";
//        String password = "123456789";
//        //账号解锁持续时间 单位秒 缺省值300秒
//        BigInteger unlockDuration = BigInteger.valueOf(60L);
//        try {
//            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, password, unlockDuration).send();
//            Boolean isUnlocked = personalUnlockAccount.accountUnlocked();
//            System.out.println("account unlock " + isUnlocked);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public String getClientVersion() {
        String version = null;
        try {
            version = web3j.web3ClientVersion().sendAsync().get().getWeb3ClientVersion();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return version;
    }

    /**
     * Sends `amount` ether from the main account to `address`
     */
//    public void sendEth(String address, Long amount) {
//        try {
//            TransactionReceipt receipt = Transfer.sendFunds(
//                    web3j, credentials, address, BigDecimal.valueOf(amount), Convert.Unit.ETHER);
//        } catch (Exception e) {
//            e.printStackTrace();
//            this.close();
//        }
//    }

//    public void deployStreak() {
//        try {
//            contract = Streak.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT, INITIAL_WEI).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void loadStreak() {
//        try {
//            contract = Streak.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void loadStreak(String address) {
//        try {
//            contract = Streak.load(address, web3j, credentials, GAS_PRICE, GAS_LIMIT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
}
