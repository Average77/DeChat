package com.isabella.dechat.core;

/**
 * Created by muhanxi on 17/7/4.
 */

public class JNICore {


    static {
        System.loadLibrary("md5");
    }
    public static native String getSign(String sign);

}
