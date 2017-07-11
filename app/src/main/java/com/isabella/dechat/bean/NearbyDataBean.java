package com.isabella.dechat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * description
 * Created by 张芸艳 on 2017/7/11.
 */
@Entity
public class NearbyDataBean {
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
    @Id(autoincrement = true)
    private Long id;
    private String createtime;
    private String phone;
    private String area;
    private String nickname;
    private int userId;
    private String introduce;
    private String gender;
    private String password;
    private double lat;
    private double lng;
    private int picHeight;
    private int picWidth;
    private String imagePath;
    private long lasttime;
    private int age;

    @Generated(hash = 703645286)
    public NearbyDataBean(Long id, String createtime, String phone, String area,
            String nickname, int userId, String introduce, String gender,
            String password, double lat, double lng, int picHeight, int picWidth,
            String imagePath, long lasttime, int age) {
        this.id = id;
        this.createtime = createtime;
        this.phone = phone;
        this.area = area;
        this.nickname = nickname;
        this.userId = userId;
        this.introduce = introduce;
        this.gender = gender;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.picHeight = picHeight;
        this.picWidth = picWidth;
        this.imagePath = imagePath;
        this.lasttime = lasttime;
        this.age = age;
    }

    @Generated(hash = 1286482681)
    public NearbyDataBean() {
    }

    public long getLasttime() {
        return lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public double getLat() {
        return lat;
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
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
