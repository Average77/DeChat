package com.isabella.dechat.core;



public class JNICore {


    static {
        System.loadLibrary("md5");
    }
    public static native String getSign(String sign);

}
