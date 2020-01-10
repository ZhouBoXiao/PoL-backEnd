package com.whu;

import com.alibaba.fastjson.JSONArray;
import com.monitorjbl.xlsx.StreamingReader;
import com.whu.contract.EthereumApi;
import com.whu.contract.GeoHashCon;
import com.whu.contract.singleton.ContractApi;
import com.whu.spatialIndex.geohash.GeoHash;
import com.whu.tools.Constant;
import com.whu.tools.LocateInfo;
import com.whu.tools.POI;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.whu.tools.GCJ02_BD09.gcj02_To_Bd09;

/**
 * Unit test for simple App.
 */
public class AppTest1
{


//    class DoWork implements Runnable {
//        Sheet sheet;
//        GeoHashCon geoHashCon;
//        GeoHash geoHash;
//        int index;
//        DoWork(int index, Sheet sheet, GeoHashCon geoHashCon, GeoHash geoHash){
//            this.index = index;
//            this.sheet = sheet;
//            this.geoHashCon = geoHashCon;
//            this.geoHash = geoHash;
//        }
//
//
//        @Override
//        public void run() {
//            LocateInfo converBD;
//            POI poi = new POI();
//            double tmpLong = 0;
//            boolean flag = false;
//            int columnIndex;
//            for (Row r : sheet) {
//                if(!flag){
//                    flag = true;
//                    continue;
//                }
//                for (Cell c : r) {
//                    columnIndex = c.getColumnIndex();
//
////                    System.out.println(c.getStringCellValue());
//                    switch (columnIndex) {
//
//                        case 0:
//                            poi.setName(c.getStringCellValue());
//                            break;
//                        case 2:
//                            tmpLong = c.getNumericCellValue();
//                            break;
//                        case 3:
//                            converBD = gcj02_To_Bd09(tmpLong, c.getNumericCellValue());
//                            poi.setLongitude(converBD.getLongitude());
//                            poi.setLatitude(converBD.getLatitude());
//
//                            break;
//                        case 4:
//                            poi.setType(c.getStringCellValue());
//                            break;
//                        case 5:
//                            poi.setArea(c.getStringCellValue());
//                            break;
//                        case 6:
//                            poi.setAddress(c.getStringCellValue());
//                            break;
//                        default:
//                            break;
//                    }
//
//                }
//                geoHash.setMergeBits(poi.getLongitude(), poi.getLatitude(), 40);
//                TransactionReceipt transactionReceipt = null;
//                try {
//                    transactionReceipt = geoHashCon.insert(geoHash.getBase32FromBits(), poi.toString()).send();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (transactionReceipt.isStatusOK()) {
//                    System.out.println(index + "add success!!");
//                } else {
//                    System.out.println(index + "add  fail!!");
//                }
//
//            }
//        }
//
//    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void should()
    {

        InputStream is = null;
        try {
//            is = new FileInputStream(new File("D:\\temp.xlsx"));
//            Workbook workbook = StreamingReader.builder()
//                    .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
//                    .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
//                    .open(is);            // InputStream or File for XLSX file (required)

//            for (Sheet sheet : workbook){
//                System.out.println(sheet.getSheetName());
//                for (Row r : sheet) {
//                    for (Cell c : r) {
//                        System.out.println(c.getStringCellValue());
//                    }
//                }
//            }
//            LocateInfo TestGD = new LocateInfo();
            GeoHashCon geoHashCon = ContractApi.getInstance().geoHashCon;
            String tmp = "wt3";
            StringBuilder all = new StringBuilder();

            for (char c :GeoHash.base32Table){
//                all+=tmp+c+",";
//                if (c==GeoHash.base32Table[GeoHash.base32Table.length-1]){
//                    all.append(tmp).append(c);
//                }
//                else{
//                    all.append(tmp).append(c).append(",");
//                }
                all.append(geoHashCon.BoundingBoxQuery(tmp +c).send());
            }
            JSONArray tmparray = JSONArray.parseArray("[" + all.substring(0, all.length()-1) + "]");
//            System.out.println(all.toString());
//            String arrlist = geoHashCon.BoundingBoxQuery(all.toString()).send();
            System.out.println("result: " + tmparray.size());
//            GeoHash geoHash = GeoHash.getInstance();
//            for (int i = 1; i < 11; i++) {
//                Thread t = new Thread(new DoWork(i, workbook.getSheetAt(i), geoHashCon, geoHash));
//                t.start();
//            }
//            ExecutorService executorService = Executors.newCachedThreadPool();
//            for (int i = 0; i < 10; i++) {
//                Future future = executorService.submit(new DoWork(i, workbook.getSheetAt(i), geoHashCon, geoHash));
//                System.out.println(future.get());
//            }
//            ExecutorService executorService1 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService2 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService3 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService4 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService5 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService6 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService7 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService8 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService9 = Executors.newSingleThreadExecutor();
//            ExecutorService executorServicea = Executors.newSingleThreadExecutor();
//            System.out.println(workbook.getNumberOfSheets());
////            Future future = executorService1.submit(new DoWork(9, workbook.getSheetAt(9), geoHashCon, geoHash));
//            Future future1 = executorService2.submit(new DoWork(10, workbook.getSheetAt(10), geoHashCon, geoHash));
//            Future future2 = executorService3.submit(new DoWork(11, workbook.getSheetAt(11), geoHashCon, geoHash));
//            Future future3 = executorService4.submit(new DoWork(12, workbook.getSheetAt(12), geoHashCon, geoHash));
//            Future future4 = executorService5.submit(new DoWork(13, workbook.getSheetAt(13), geoHashCon, geoHash));
//            Future future5 = executorService6.submit(new DoWork(14, workbook.getSheetAt(14), geoHashCon, geoHash));
//            Future future6 = executorService7.submit(new DoWork(15, workbook.getSheetAt(15), geoHashCon, geoHash));
//            Future future7 = executorService8.submit(new DoWork(16, workbook.getSheetAt(16), geoHashCon, geoHash));
//            Future future8 = executorService9.submit(new DoWork(17, workbook.getSheetAt(17), geoHashCon, geoHash));
//            Future future9 = executorServicea.submit(new DoWork(18, workbook.getSheetAt(18), geoHashCon, geoHash));
////            executorService2.execute(new DoWork(1, workbook.getSheetAt(1), geoHashCon, geoHash));
////            System.out.println(future.get());
//            System.out.println(future1.get());
//            System.out.println(future2.get());
//            System.out.println(future3.get());
//            System.out.println(future4.get());
//            System.out.println(future5.get());
//            System.out.println(future6.get());
//            System.out.println(future7.get());
//            System.out.println(future8.get());
//            System.out.println(future9.get());
//            executorService.shutdown();
//            Sheet sheet = workbook.getSheet("美食");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void print1() throws Exception {
        GeoHashCon geoHashCon = ContractApi.getInstance().geoHashCon;
        String tmp = "w";
        String arr = geoHashCon.BoundingBoxQuery(tmp ).send();
        JSONArray tmparray = JSONArray.parseArray("[" + arr.substring(0, arr.length()-1) + "]");
        System.out.println("before: " + tmparray.size());

        geoHashCon.insert("wt3npymqb","{\"name\":\"润荣小吃1\",\"location\":[{\"xn\":\"31.000858713346712\"},{\"yn\":\"114.90069617025246\"},{\"zn\":\"0\"}],\"address\":\"潘塘路西50米\",\"type\":\"餐饮服务;快餐厅;快餐厅\",\"area\":\"新洲区\"}" );
        arr = geoHashCon.BoundingBoxQuery(tmp ).send();
        tmparray = JSONArray.parseArray("[" + arr.substring(0, arr.length()-1) + "]");
        System.out.println("after: " + tmparray.size());


    }
    @Test
    public void print2() throws Exception {
        GeoHashCon geoHashCon = ContractApi.getInstance().geoHashCon;
        geoHashCon.insert("wt3pp54j6", "{\"name\":\"1楼梯(百联奥特莱斯广场)\",\"location\":[{\"xn\":\"30.737198871255774\"},{\"yn\":\"114.26209253459466\"},{\"zn\":\"0\"}],\"address\":\"盘龙1城盘龙大道51号百联奥特莱斯广场2F层\",\"type\":\"室内设施;室内设施;室内设施\",\"area\":\"黄陂\"}").send();
//        String tmp = "w";
//
//        String arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//        tmp = "wt";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//
//        tmp = "wt3";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//        tmp = "wt9";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//
//        tmp = "wt93";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//        tmp = "wt932";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//        tmp = "wt932j";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//        tmp = "wt932j7";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//        tmp = "wt932j7e";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
//        tmp = "wt932j7ex";
//
//        arr = geoHashCon.print(tmp).send();
//        System.out.println(arr);
    }
}
