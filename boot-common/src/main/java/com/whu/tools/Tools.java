package com.whu.tools;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    private static Logger logger = LoggerFactory.getLogger(Tools.class);
    /**
     * 生成临时文件
     * @param json
     * @return
     */
    public static File createTempFile(String json){
        FileOutputStream fos = null;
        //创建临时钱包文件
        File walletFile = null;
        ObjectOutputStream out = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

            String s = sd.format(new Date());
            walletFile = File.createTempFile(s, ".json");

            logger.info("临时文件所在的本地路径：" , walletFile.getCanonicalPath());
            fw = new FileWriter(walletFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭临时文件
            try {
                assert bw != null;
                bw.flush();
                fw.flush();
                bw.close();
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return walletFile;

    }
}
