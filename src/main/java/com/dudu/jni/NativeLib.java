package com.dudu.jni;

import java.net.URL;

public class NativeLib {
    static {
        LibLoader.loadLib("jniLibs/libNativeLib.so");
    }
//    static {
//        URL url = NativeLib.class.getClassLoader().getResource("jniLibs/libNativeLib.so");
//        System.load(url.getPath());
//    }

//    public static void main(String[] args) {
//        String uuid = gen();
//        System.out.println("generateUUID: " + uuid);
//    }

    public static native String genProof(String position);
    public static native String generateUUID();
}