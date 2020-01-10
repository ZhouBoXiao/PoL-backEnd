package com.whu;

import static com.whu.tools.Constant.geoHashConContractAddress;
import static com.whu.tools.GCJ02_BD09.gcj02_To_Bd09;
import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monitorjbl.xlsx.StreamingReader;
import com.whu.contract.EthereumApi;
import com.whu.contract.GeoHashCon;
import com.whu.contract.singleton.ContractApi;
import com.whu.spatialIndex.geohash.GeoHash;
import com.whu.tools.Constant;
import com.whu.tools.CreateFileUtil;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    JSONArray jsonObject = new JSONArray();
    Map<String, Object> agencyMap = new HashMap<>();

    @Test
    public void shouldA() {
        GeoHash g = new GeoHash(114.3982544388468,31.040243877383816);
        System.out.println(g.getBase32FromBits());
    }

    class DoWork implements Runnable {
        Sheet sheet;
        GeoHashCon geoHashCon;
        GeoHash geoHash;
        int index;
        DoWork(int index, Sheet sheet, GeoHashCon geoHashCon, GeoHash geoHash){
            this.index = index;
            this.sheet = sheet;
            this.geoHashCon  = GeoHashCon.load(Constant.geoHashConContractAddress,
                    EthereumApi.getInstance().web3j,
                    EthereumApi.getInstance().transactionManager,
                    EthereumApi.getInstance().contractGasProvider
            );
            this.geoHash = geoHash;
        }


        @Override
        public void run() {
            LocateInfo converBD;
            POI poi = new POI();
            double tmpLong = 0;
            boolean flag = false;
            for (Row r : sheet) {
                if(!flag){
                    flag = true;
                    continue;
                }
                for (Cell c : r) {
                    int columnIndex = c.getColumnIndex();

//                    System.out.println(c.getStringCellValue());
                    switch (columnIndex) {

                        case 0:
                            poi.setName(c.getStringCellValue());
                            break;
                        case 2:
                            tmpLong = c.getNumericCellValue();
                            break;
                        case 3:
                            converBD = gcj02_To_Bd09(tmpLong, c.getNumericCellValue());
                            poi.setLongitude(converBD.getLongitude());
                            poi.setLatitude(converBD.getLatitude());

                            break;
                        case 4:
                            poi.setType(c.getStringCellValue());
                            break;
                        case 5:
                            poi.setArea(c.getStringCellValue());
                            break;
                        case 6:
                            poi.setAddress(c.getStringCellValue());
                            break;
                        default:
                            break;
                    }

                }
                geoHash.setMergeBits(poi.getLongitude(), poi.getLatitude(), 45);
                TransactionReceipt transactionReceipt = null;
                try {
//                    System.out.println(poi.getLongitude() + "  " + poi.getLatitude());
                    System.out.println(geoHash.getBase32FromBits());
                    transactionReceipt = geoHashCon.insert(geoHash.getBase32FromBits(), poi.toString()).send();
//                    JSONObject json = new JSONObject();
//                    json.put("geohash",geoHash.getBase32FromBits());
//                    json.put("poi", poi.toString());
////                    System.out.println(poi.toString());
//                    jsonObject.add(json);
//                    System.out.println(jsonObject.size());
//                    if (jsonObject.size() >= 50000){
//                        String jsonString1 = jsonObject.toString();
//                        System.out.println(jsonString1);
//                        CreateFileUtil.createJsonFile(jsonString1, "D:\\", "agency1");
//                        break;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                if (transactionReceipt.isStatusOK()) {
//                    System.out.println(index + "add success!!");
//                } else {
//                    System.out.println(index + "add  fail!!");
//                }

            }
        }

    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

        InputStream is = null;
        try {
            is = new FileInputStream(new File("D:\\temp.xlsx"));
            Workbook workbook = StreamingReader.builder()
                    .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                    .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                    .open(is);            // InputStream or File for XLSX file (required)

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
            ExecutorService executorService1 = Executors.newSingleThreadExecutor();
            ExecutorService executorService2 = Executors.newSingleThreadExecutor();
            ExecutorService executorService3 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService4 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService5 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService6 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService7 = Executors.newSingleThreadExecutor();
            ExecutorService executorService8 = Executors.newSingleThreadExecutor();
//            ExecutorService executorService9 = Executors.newSingleThreadExecutor();
//            ExecutorService executorServicea = Executors.newSingleThreadExecutor();
//            ExecutorService executorServiceb = Executors.newSingleThreadExecutor();
//            ExecutorService executorServicec = Executors.newSingleThreadExecutor();


            Future future = executorService1.submit(new DoWork(2, workbook.getSheetAt(4), geoHashCon, new GeoHash()));
            Future future1 = executorService2.submit(new DoWork(1, workbook.getSheetAt(1), geoHashCon, new GeoHash()));
            Future future2 = executorService3.submit(new DoWork(3, workbook.getSheetAt(3), geoHashCon, new GeoHash()));
//            Future future3 = executorService4.submit(new DoWork(4, workbook.getSheetAt(4), geoHashCon, new GeoHash()));
//            Future future4 = executorService5.submit(new DoWork(5, workbook.getSheetAt(5), geoHashCon, new GeoHash()));
//            Future future5 = executorService6.submit(new DoWork(6, workbook.getSheetAt(6), geoHashCon, new GeoHash()));
//            Future future6 = executorService7.submit(new DoWork(7, workbook.getSheetAt(7), geoHashCon, new GeoHash()));
            Future future7 = executorService8.submit(new DoWork(16, workbook.getSheetAt(16), geoHashCon, new GeoHash()));
//            Future future8 = executorService9.submit(new DoWork(9, workbook.getSheetAt(9), geoHashCon, new GeoHash()));
//            Future future9 = executorServicea.submit(new DoWork(10, workbook.getSheetAt(10), geoHashCon, new GeoHash()));
//            Future futurea = executorServiceb.submit(new DoWork(11, workbook.getSheetAt(11), geoHashCon, new GeoHash()));
//            Future futureb = executorServicec.submit(new DoWork(12, workbook.getSheetAt(12), geoHashCon, new GeoHash()));

//            Future future = executorService1.submit(new DoWork(21, workbook.getSheetAt(21), geoHashCon, new GeoHash()));
//            Future future1 = executorService2.submit(new DoWork(14, workbook.getSheetAt(14), geoHashCon, new GeoHash()));
//            Future future2 = executorService3.submit(new DoWork(15, workbook.getSheetAt(15), geoHashCon, new GeoHash()));
//            Future future3 = executorService4.submit(new DoWork(16, workbook.getSheetAt(16), geoHashCon, new GeoHash()));
//            Future future4 = executorService5.submit(new DoWork(17, workbook.getSheetAt(17), geoHashCon, new GeoHash()));
//            Future future5 = executorService6.submit(new DoWork(18, workbook.getSheetAt(18), geoHashCon, new GeoHash()));
//            Future future6 = executorService7.submit(new DoWork(19, workbook.getSheetAt(19), geoHashCon, new GeoHash()));
//            Future future7 = executorService8.submit(new DoWork(20, workbook.getSheetAt(20), geoHashCon, new GeoHash()));
//            Future future8 = executorService9.submit(new DoWork(21, workbook.getSheetAt(21), geoHashCon, new GeoHash()));
//            Future future9 = executorServicea.submit(new DoWork(22, workbook.getSheetAt(22), geoHashCon, new GeoHash()));
//            Future futurea = executorServiceb.submit(new DoWork(0, workbook.getSheetAt(0), geoHashCon, new GeoHash()));
//            Future futureb = executorServicec.submit(new DoWork(12, workbook.getSheetAt(12), geoHashCon, new GeoHash()));

//            executorService2.execute(new DoWork(1, workbook.getSheetAt(1), geoHashCon, geoHash));
            System.out.println(future.get());
            System.out.println(future1.get());
            System.out.println(future2.get());
//            System.out.println(future3.get());
//            System.out.println(future4.get());
//            System.out.println(future5.get());
//            System.out.println(future6.get());
            System.out.println(future7.get());
//            System.out.println(future8.get());
//            System.out.println(future9.get());
//            System.out.println(futurea.get());
//            System.out.println(futureb.get());

//            executorService.shutdown();
//            Sheet sheet = workbook.getSheet("美食");


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {


        }


    }
}
