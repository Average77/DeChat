package com.isabella.dechat.bean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/6/23.
 */

public class Friend {

    /**
     * result_message : success
     * result_code : 200
     * date : [{"createtime":"Jun 23, 2017 9:37:57 AM","phone":"2222","area":"beijing","imagePath":"D:\\apache-tomcat-7.0.78\\webapps\\MyInterface\\images837834eb-d1bc-47ae-8e91-8b184b4ed102.png","nickname":"Average","userId":4,"introduce":"aaa","password":"1234"},{"createtime":"Jun 23, 2017 9:37:57 AM","phone":"2222","area":"beijing","imagePath":"D:\\apache-tomcat-7.0.78\\webapps\\MyInterface\\images837834eb-d1bc-47ae-8e91-8b184b4ed102.png","nickname":"Average","userId":4,"introduce":"aaa","password":"1234"}]
     */

    private String result_message;
    private int result_code;
    private List<DateBean> date;

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

    public List<DateBean> getDate() {
        return date;
    }

    public void setDate(List<DateBean> date) {
        this.date = date;
    }

    public static class DateBean {
        /**
         * createtime : Jun 23, 2017 9:37:57 AM
         * phone : 2222
         * area : beijing
         * imagePath : D:\apache-tomcat-7.0.78\webapps\MyInterface\images837834eb-d1bc-47ae-8e91-8b184b4ed102.png
         * nickname : Average
         * userId : 4
         * introduce : aaa
         * password : 1234
         */

        private String createtime;
        private String phone;
        private String area;
        private String imagePath;
        private String nickname;
        private int userId;
        private String introduce;
        private String password;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "DateBean{" +
                    "createtime='" + createtime + '\'' +
                    ", phone='" + phone + '\'' +
                    ", area='" + area + '\'' +
                    ", imagePath='" + imagePath + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", userId=" + userId +
                    ", introduce='" + introduce + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Friend{" +
                "result_message='" + result_message + '\'' +
                ", result_code=" + result_code +
                ", date=" + date +
                '}';
    }
}
