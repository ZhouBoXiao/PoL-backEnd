package com.dudu.jni;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;

public class LibLoader {
    public static void loadLib(String libName) {
        String resourcePath = "/" + libName;
        String folderName = System.getProperty("java.io.tmpdir") + "/lib/";
        File folder = new File(folderName);
        folder.mkdirs();
        File libFile = new File(folder, libName);
        if (libFile.exists()) {
            System.load(libFile.getAbsolutePath());
        } else {
            try {
                InputStream in = LibLoader.class.getResourceAsStream(resourcePath);
                FileUtils.copyInputStreamToFile(in, libFile);

                in.close();
                System.load(libFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load required lib", e);
            }
        }
    }
}
