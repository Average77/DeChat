package com.isabella.dechat.bean;



public class LoginBean {

    /**
     * result_message : success
     * result_code : 200
     * data : {"createtime":1497604048000,"phone":"123","area":"北京","lasttime":1497604196266,"nickname":"Tryndamere","userId":2,"introduce":"我就是我不一样的烟火","password":"123"}
     */

    private String result_message;
    private int result_code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 1497604048000
         * phone : 123
         * area : 北京
         * lasttime : 1497604196266
         * nickname : Tryndamere
         * userId : 2
         * introduce : 我就是我不一样的烟火
         * password : 123
         */

        private long createtime;
        private String phone;
        private String area;
        private long lasttime;
        private String nickname;
        private int userId;
        private String introduce;
        private String password;
        private double lat;
        private double lng;
        private String yxpassword;
        private String imagePath;

        public String getImagepath() {
            return imagePath;
        }

        public void setImagepath(String imagepath) {
            this.imagePath = imagepath;
        }

        public double getLat() {
            return lat;
        }

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
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

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
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
            return "UserInfoDataBean{" +
                    "createtime=" + createtime +
                    ", phone='" + phone + '\'' +
                    ", area='" + area + '\'' +
                    ", lasttime=" + lasttime +
                    ", nickname='" + nickname + '\'' +
                    ", userId=" + userId +
                    ", introduce='" + introduce + '\'' +
                    ", password='" + password + '\'' +
                    ", lat=" + lat +
                    ", lng=" + lng +
                    ", imagepath='" + imagePath + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "result_message='" + result_message + '\'' +
                ", result_code=" + result_code +
                ", data=" + data +
                '}';
    }
}
