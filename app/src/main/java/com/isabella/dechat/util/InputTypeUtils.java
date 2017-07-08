package com.isabella.dechat.util;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class InputTypeUtils {
    private static volatile InputTypeUtils inputTypeUtils=null;
    private InputTypeUtils(){}
    public static InputTypeUtils getInstance(){
        if (inputTypeUtils==null){
            synchronized (InputTypeUtils.class){
                if (inputTypeUtils==null){
                    inputTypeUtils=new InputTypeUtils();
                }
            }
        }
        return inputTypeUtils;
    }


}
