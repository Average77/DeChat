package com.isabella.dechat.bean;

/**
 * description
 * Created by 张芸艳 on 2017/6/23.
 */

public class Info {

    /**
     * userId : 2
     * nickname : Ttyndamere
     * password : 123
     * gender : ç·
     * area : åäº¬
     * phone : 13353910788
     * introduce : The Stupid Man!
     * createtime : Jun 23, 2017 8:59:30 AM
     */

    private int userId;
    private String nickname;
    private String password;
    private String gender;
    private String area;
    private String phone;
    private String introduce;
    private String createtime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Info{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", area='" + area + '\'' +
                ", phone='" + phone + '\'' +
                ", introduce='" + introduce + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
