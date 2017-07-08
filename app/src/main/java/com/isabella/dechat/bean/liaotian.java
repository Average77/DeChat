package com.isabella.dechat.bean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/6/23.
 */

public class liaotian {

    /**
     * result_message : success
     * result_code : 200
     * data : [{"message":"111","userId":7,"touserId":6,"chatId":4},{"message":"ç\u009a\u0084ç»§ç\u0088¶å\u009b\u009eå®¶","userId":7,"touserId":6,"chatId":7},{"message":"1112","userId":7,"touserId":6,"chatId":8},{"message":"1112","userId":7,"touserId":6,"chatId":9}]
     */

    private String result_message;
    private int result_code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * message : 111
         * userId : 7
         * touserId : 6
         * chatId : 4
         */

        private String message;
        private int userId;
        private int touserId;
        private int chatId;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTouserId() {
            return touserId;
        }

        public void setTouserId(int touserId) {
            this.touserId = touserId;
        }

        public int getChatId() {
            return chatId;
        }

        public void setChatId(int chatId) {
            this.chatId = chatId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "message='" + message + '\'' +
                    ", userId=" + userId +
                    ", touserId=" + touserId +
                    ", chatId=" + chatId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "liaotian{" +
                "result_message='" + result_message + '\'' +
                ", result_code=" + result_code +
                ", data=" + data +
                '}';
    }
}
