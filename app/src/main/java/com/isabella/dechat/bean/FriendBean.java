package com.isabella.dechat.bean;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class FriendBean {

    /**
     * result_message : 添加好友成功！
     * result_code : 200
     */

    private String result_message;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    @Override
    public String toString() {
        return "FriendBean{" +
                "result_message='" + result_message + '\'' +
                ", result_code=" + result_code +
                '}';
    }
}
