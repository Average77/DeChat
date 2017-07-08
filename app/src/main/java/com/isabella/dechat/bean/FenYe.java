package com.isabella.dechat.bean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/6/22.
 */

public class FenYe {

    /**
     * result_code : 200
     * pageCount : 共10条记录
     * data : [{"createtime":"Jun 22, 2017 10:12:33 AM","phone":"123","area":"1212","nickname":"12121231","userId":3,"introduce":"1212","gender":"a","password":"456"},{"createtime":"Jun 22, 2017 5:33:07 PM","phone":"15313095207","area":"å\u008c\u0097äº¬","nickname":"LZM","userId":4,"introduce":"æ\u0088\u0091å°±æ\u0098¯æ\u0088\u0091ä¸\u008dä¸\u0080æ ·ç\u009a\u0084ç\u0083\u009fç\u0081«","password":"123"},{"createtime":"Jun 22, 2017 6:45:41 PM","phone":"1212","area":"å\u008c\u0097äº¬","nickname":"Average","userId":5,"introduce":"æ\u0088\u0091å°±æ\u0098¯æ\u0088\u0091ä¸\u008dä¸\u0080æ ·ç\u009a\u0084ç\u0083\u009fç\u0081«","password":"123"},{"createtime":"Jun 22, 2017 6:58:43 PM","phone":"1313","area":"å\u008c\u0097äº¬","nickname":"Average","userId":6,"introduce":"æ\u0088\u0091å°±æ\u0098¯æ\u0088\u0091ä¸\u008dä¸\u0080æ ·ç\u009a\u0084ç\u0083\u009fç\u0081«","password":"123"},{"createtime":"Jun 22, 2017 7:19:13 PM","phone":"1414","area":"beijing","nickname":"Average","userId":7,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 7:23:06 PM","phone":"1515","area":"beijing","nickname":"Average","userId":8,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 7:33:28 PM","phone":"1616","area":"beijing","nickname":"Average","userId":9,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 7:42:45 PM","phone":"1717","area":"145","nickname":"545","userId":10,"introduce":"121","password":"666"},{"createtime":"Jun 22, 2017 9:27:11 PM","phone":"1818","area":"beijing","nickname":"Average","userId":11,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 9:39:28 PM","phone":"1919","area":"beijing","nickname":"Average","userId":12,"introduce":"aaa","password":"123"}]
     */

    private int result_code;
    private String pageCount;
    private List<DataBean> data;
    private String result_message;

    public int getResult_code() {
        return result_code;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : Jun 22, 2017 10:12:33 AM
         * phone : 123
         * area : 1212
         * nickname : 12121231
         * userId : 3
         * introduce : 1212
         * gender : a
         * password : 456
         */

        private String createtime;
        private String phone;
        private String area;
        private String nickname;
        private int userId;
        private String introduce;
        private String gender;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "createtime='" + createtime + '\'' +
                    ", phone='" + phone + '\'' +
                    ", area='" + area + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", userId=" + userId +
                    ", introduce='" + introduce + '\'' +
                    ", gender='" + gender + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FenYe{" +
                "result_code=" + result_code +
                ", pageCount='" + pageCount + '\'' +
                ", data=" + data +
                ", result_message='" + result_message + '\'' +
                '}';
    }
}
