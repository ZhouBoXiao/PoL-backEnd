package com.whu.aesrsa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.TreeMap;

/**
 * Description: AES+RSA签名，加密 验签，解密
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2015/8/13 15:12
 */
public class Main {
    public static final String clientPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKbNojYr8KlqKD/y" +
            "COd7QXu3e4TsrHd4sz3XgDYWEZZgYqIjVDcpcnlztwomgjMj9xSxdpyCc85GOGa0" +
            "lva1fNZpG6KXYS1xuFa9G7FRbaACoCL31TRv8t4TNkfQhQ7e2S7ZktqyUePWYLlz" +
            "u8hx5jXdriErRIx1jWK1q1NeEd3NAgMBAAECgYAws7Ob+4JeBLfRy9pbs/ovpCf1" +
            "bKEClQRIlyZBJHpoHKZPzt7k6D4bRfT4irvTMLoQmawXEGO9o3UOT8YQLHdRLitW" +
            "1CYKLy8k8ycyNpB/1L2vP+kHDzmM6Pr0IvkFgnbIFQmXeS5NBV+xOdlAYzuPFkCy" +
            "fUSOKdmt3F/Pbf9EhQJBANrF5Uaxmk7qGXfRV7tCT+f27eAWtYi2h/gJenLrmtke" +
            "Hg7SkgDiYHErJDns85va4cnhaAzAI1eSIHVaXh3JGXcCQQDDL9ns78LNDr/QuHN9" +
            "pmeDdlQfikeDKzW8dMcUIqGVX4WQJMptviZuf3cMvgm9+hDTVLvSePdTlA9YSCF4" +
            "VNPbAkEAvbe54XlpCKBIX7iiLRkPdGiV1qu614j7FqUZlAkvKrPMeywuQygNXHZ+" +
            "HuGWTIUfItQfSFdjDrEBBuPMFGZtdwJAV5N3xyyIjfMJM4AfKYhpN333HrOvhHX1" +
            "xVnsHOew8lGKnvMy9Gx11+xPISN/QYMa24dQQo5OAm0TOXwbsF73MwJAHzqaKZPs" +
            "EN08JunWDOKs3ZS+92maJIm1YGdYf5ipB8/Bm3wElnJsCiAeRqYKmPpAMlCZ5x+Z" +
            "AsuC1sjcp2r7xw==";

    public static final String clientPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmzaI2K/Cpaig/8gjne0F7t3uE" +
            "7Kx3eLM914A2FhGWYGKiI1Q3KXJ5c7cKJoIzI/cUsXacgnPORjhmtJb2tXzWaRui" +
            "l2EtcbhWvRuxUW2gAqAi99U0b/LeEzZH0IUO3tku2ZLaslHj1mC5c7vIceY13a4h" +
            "K0SMdY1itatTXhHdzQIDAQAB";

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

    public static void main(String[] args) throws Exception {
        TreeMap<String, Object> params = new TreeMap<String, Object>();
//        params.put("userid", "152255855");
//        params.put("phone", "18965621420");
        String prover = "{\"Prover\":\"prover\",\"contractAddress\":\"0x8b99ea0c9904fd080c5774bdf7c0920fe4c23c5d\",\"location\":[{\"xn\":\"47.1666\"},{\"yn\":\"8.5\"},{\"zn\":\"425\"}],\"ZeroKnowledgeProofs\":[{\"created\":\"2017-06-18 21:19:13\",\"Witness\":\"witness01\",\"proof\":{\"initialCommitments\":{\"t_n\":\"1.\",\"b_0\":\"1091.\",\"b_1\":\"4327.\",\"t_a\":\"13300.\",\"sa\":\"9901.\"},\"response\":{\"R\":\"1276083205323423485946175437412277323947919300853910755296975.\",\"Zn\":\"926814698241222861986239039479596152328088487860588964536644.\",\"Yn\":\"5115516814512806562166280323220507246672607395643139326589.\",\"R_a\":\"358822000068848108859566009574928246032929338030824543071536482.\",\"Xn\":\"1211836435537859197334203773879642803225937508549952173604041.\",\"R_d\":\"212402462909023175593624971962967230060540307211320953948980124.\",\"A[1]\":\"1574713762718249323515370529566810318212728461253878000860741.\",\"A[0]\":\"125214058143888527695449891725116372735111058060149216607489.\",\"A[3]\":\"458767799878626827475690081762902631652740486430451300066564.\",\"A[2]\":\"1444623667730745069549092394312371349620796570494342287076176.\"},\"publicInfo\":{\"zl\":\"0.\",\"su\":\"1.\",\"yl\":\"0.\",\"xl\":\"0.\",\"d2\":\"100000000.\"},\"challenge\":{\"c\":\"1212.\"},\"parameters\":{\"h[0]\":\"16385.\",\"gx\":\"18652.\",\"gy\":\"12642.\",\"gz\":\"19445.\",\"g\":\"4323.\",\"gr\":\"17679.\",\"h[3]\":\"2153.\",\"n\":\"19673.\",\"h[2]\":\"12638.\",\"h[1]\":\"9555.\"}}},{\"created\":\"2017-06-18 21:19:13\",\"Witness\":\"witness02\",\"proof\":{\"initialCommitments\":{\"t_n\":\"1.\",\"b_0\":\"1553.\",\"b_1\":\"4529.\",\"t_a\":\"4047.\",\"sa\":\"7012.\"},\"response\":{\"R\":\"22908255399941018721440792893277973176893756366925025100085.\",\"Zn\":\"358819600333588481028944310463297469708980228999585237863522.\",\"Yn\":\"859998704025746492010068844855162254793202362120234976064271.\",\"R_a\":\"188716987378547549635328433881824952495230664685743387213965243287.\",\"Xn\":\"1534660709597081860359108273715721416374007008759723493113410.\",\"R_d\":\"58163335274716801801287911499957808252616246602690431886760002381.\",\"A[1]\":\"1392493315934239158161559166959205303243037729703047406053643.\",\"A[0]\":\"478465228263560834252451479694028271227234253935187127468191.\",\"A[3]\":\"724258088921694894450973661790956679066351503362581382947509.\",\"A[2]\":\"1319394880369395530064497088126669809474636016129193846608417.\"},\"publicInfo\":{\"zl\":\"0.\",\"su\":\"1.\",\"yl\":\"0.\",\"xl\":\"0.\",\"d2\":\"100000000.\"},\"challenge\":{\"c\":\"121332.\"},\"parameters\":{\"h[0]\":\"16385.\",\"gx\":\"18652.\",\"gy\":\"12642.\",\"gz\":\"19445.\",\"g\":\"4323.\",\"gr\":\"17679.\",\"h[3]\":\"2153.\",\"n\":\"19673.\",\"h[2]\":\"12638.\",\"h[1]\":\"9555.\"}}}]}";
        params.put("Prover", JSON.parseObject(prover));
//        String search = "{\"contractAddress\":\"0xb9a36ae8ee7a53417ed283ade4aa5f4eec70421d\",\"startTime\":\"2010-01-01 19:73:24\",\"endTime\":\"2010-01-03 19:73:24\"}";
//        params.put("search", search);
        client(params);
//        System.out.println(clientPublicKey);
        server();
    }

    public static void client(TreeMap<String, Object> params) throws Exception {
        // 生成RSA签名
        String sign = EncryUtil.handleRSA(params, clientPrivateKey);
        params.put("sign", sign);

        String info = JSON.toJSONString(params);
        //随机生成AES密钥
        String aesKey = SecureRandomUtil.getRandom(16);
        //AES加密数据
        String data = AES.encryptToBase64(ConvertUtils.stringToHexString(info), aesKey);

        // 使用RSA算法将商户自己随机生成的AESkey加密
        String encryptkey = RSA.encrypt(aesKey, serverPublicKey);

        Req.data = data;
        Req.encryptkey = encryptkey;

        System.out.println("加密后的请求数据:\n" + new Req().toString());
    }

    public static void server() throws Exception {

        // 验签
        boolean passSign = EncryUtil.checkDecryptAndSign(Req.data,
                Req.encryptkey, clientPublicKey, serverPrivateKey);
//        System.out.println(clientPublicKey);
//        System.out.println(serverPrivateKey);
        if (passSign) {
            // 验签通过
            String aeskey = RSA.decrypt(Req.encryptkey,
                    serverPrivateKey);
            String data = ConvertUtils.hexStringToString(AES.decryptFromBase64(Req.data,
                    aeskey));

            JSONObject jsonObj = JSONObject.parseObject(data);
            String prover = jsonObj.getString("Prover");
//            String prover = jsonObj.getString("search");


            System.out.println("解密后的明文:" + prover);

        } else {
            System.out.println("验签失败");
        }
    }

    static class Req {
        public static String data;
        public static String encryptkey;

        @Override
        public String toString() {
            return "data:" + data + "\nencryptkey:" + encryptkey;
        }
    }
}