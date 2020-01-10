package com.whu.contract;

import com.whu.tools.Constant;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.rmi.StubNotFoundException;
import java.util.concurrent.CompletableFuture;

import static com.whu.tools.Constant.geoHashConContractAddress;
import static com.whu.tools.Constant.verifyManagerContractAddress;
import static com.whu.tools.Tools.selectProvider;

public class Main {

    public static void main(String[] args) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(selectProvider(Constant.provideAddr)));
        System.out.println("[ETH-INFO] Connected to TestRPC");
        Credentials credentials = WalletUtils.loadCredentials(
                "12345678", "C:\\Users\\Administrator\\keystores\\admin.json");
//        TransactionManager transactionManager = new RawTransactionManager(
//                //尝试轮询0x20次，interval is 1000ms
//                web3j, credentials, 7, 20, 100);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        CertTest certQuery = CertTest.load("0x253bfe8df6496e0fc5b87e0f97d93fc0639ca76c",
                web3j,
                credentials,
                contractGasProvider
        );
        VerifyManager verifyManager = VerifyManager.load(verifyManagerContractAddress,
                web3j,
                credentials,
                contractGasProvider);
        String array="[{\"response\":{\"Xn\":\"420961641888242250610758055033562471538955040618114900625674.\",\"Yn\":\"986949617852941654031087023582669768135846038544591252836646.\",\"Zn\":\"208607609146470375237628957361233365760680053751655710329889.\",\"R\":\"545104821839293462570522002359151614281155636422554902739433882662409298178.\",\"R_a\":\"731041370768481775735138492614968487346969486612488502210861867296338622927.\",\"R_d\":\"541726811825208524730401629968792263406656041742080398821113884103687292450.\",\"A[0]\":\"1333561648688507264482417296848977896418391322569892435989787.\",\"A[1]\":\"226226025038992125143251477633060407473669124392695808187654.\",\"A[2]\":\"1017160478749795280884014249733514449223776827078462959199426.\",\"A[3]\":\"1089146806689987225410522776719741951842514376176214489218327.\"},\"initialCommitments\":{\"sa\":\"11299.\",\"t_n\":\"4967.\",\"t_a\":\"11446.\",\"b_1\":\"885.\",\"b_0\":\"7645.\"},\"challenge\":{\"c\":\"901872000786622.\"},\"publicInfo\":{\"xl\":\"0.\",\"yl\":\"0.\",\"zl\":\"0.\",\"su\":\"7244.\",\"d2\":\"100000000.\"},\"parameters\":{\"n\":\"19673.\",\"gx\":\"18652.\",\"gy\":\"12642.\",\"gz\":\"19445.\",\"g\":\"4323.\",\"gr\":\"17679.\",\"h[0]\":\"16385.\",\"h[1]\":\"9555.\",\"h[2]\":\"12638.\",\"h[3]\":\"2153.\"}}]";
        long endTime,startTime;
        for(int j=0;j<6;j++) {
            startTime = System.nanoTime();   //获取开始时间
            for (int i = 0; i < 1; i++) {
                certQuery.addCertificate(
                        "1212", "1211").send();
            }
            endTime = System.nanoTime(); //获取结束时间

            System.out.println("程序运行时间： " + (endTime - startTime) * 1.0 / 1000000 + "ms");
            startTime = System.nanoTime();
            for (int i = 0; i < 1; i++) {
                certQuery.search("1211", "1214").send();

            }
            endTime = System.nanoTime();
            System.out.println("程序运行时间： " + (endTime - startTime) * 1.0 / 1000000 + "ms");
            startTime = System.nanoTime();
            for (int i = 0; i < 1; i++) {
                verifyManager.verify(array).send();
            }
            endTime = System.nanoTime();
            System.out.println("程序运行时间： " + (endTime - startTime) * 1.0 / 1000000 + "ms");
//        System.out.println(result);

        }

//        System.out.println("ok");
    }
}
