package com.isabella.dechat.bean;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class FriendBean {


    /**
     * result_message : 添加好友成功
     * addUser : {"area":"河北省-邯郸市-磁县","picWidth":720,"createtime":1500029934770,"picHeight":883,"gender":"男","lng":116.293986,"introduce":"1111","imagePath":"http://qhb.2dyt.com/MyInterface/images/4990b462-5961-435b-8a3d-2ecea9e2b6db.jpg","userId":12,"yxpassword":"P1Emm0t2","relation":0,"password":"c4ca4238a0b923820dcc509a6f75849b","lasttime":1500031205398,"phone":"13582305284","nickname":"叶子","age":"20","lat":40.039216}
     * result_code : 200
     */

    private String result_message;
    private AddUserBean addUser;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public AddUserBean getAddUser() {
        return addUser;
    }

    public void setAddUser(AddUserBean addUser) {
        this.addUser = addUser;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public static class AddUserBean {
        /**
         * area : 河北省-邯郸市-磁县
         * picWidth : 720
         * createtime : 1500029934770
         * picHeight : 883
         * gender : 男
         * lng : 116.293986
         * introduce : 1111
         * imagePath : http://qhb.2dyt.com/MyInterface/images/4990b462-5961-435b-8a3d-2ecea9e2b6db.jpg
         * userId : 12
         * yxpassword : P1Emm0t2
         * relation : 0
         * password : c4ca4238a0b923820dcc509a6f75849b
         * lasttime : 1500031205398
         * phone : 13582305284
         * nickname : 叶子
         * age : 20
         * lat : 40.039216
         */

        private String area;
        private int picWidth;
        private long createtime;
        private int picHeight;
        private String gender;
        private double lng;
        private String introduce;
        private String imagePath;
        private int userId;
        private String yxpassword;
        private int relation;
        private String password;
        private long lasttime;
        private String phone;
        private String nickname;
        private String age;
        private double lat;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getPicWidth() {
            return picWidth;
        }

        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
