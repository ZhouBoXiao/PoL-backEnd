package com.whu.jni;

public class NativeLib {
    static {
        LibLoader.loadLib("jniLibs/bNativeLib.so");
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