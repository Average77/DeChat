package com.isabella.dechat.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

/**
 * description
 * Created by 张芸艳 on 2017/6/21.
 */

public class GsonUtil {
    private static volatile GsonUtil gsonUtil = null;
    private static Gson gson;

    private GsonUtil() {
        gson = new Gson();
    }

    public static GsonUtil getInstance() {
        if (gsonUtil == null) {
            synchronized (GsonUtil.class) {
                if (gsonUtil == null) {
                    gsonUtil = new GsonUtil();
                }
            }
        }

        return gsonUtil;
    }
    //gson解析
    public <T> T fromJson(String s,Class<T> classType){
        T t = gson.fromJson(s, classType);
        return t;

    }
    //fastJson 方式
    public <T> T parseObject(String s, Class<T> classType) {
        T t = JSON.parseObject(s, classType);
        return t;

    }
}
