package com.isabella.dechat.bean;



public class ChatBean {

    /**
     * result_message : success
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
        return "ChatBean{" +
                "result_message='" + result_message + '\'' +
                ", result_code=" + result_code +
                '}';
    }
}
