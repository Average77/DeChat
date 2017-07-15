package com.isabella.dechat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

@Entity
public class UserInfoDataBean {
    @Id(autoincrement = true)
    Long id;
    @Transient
    private String area;
    @Transient
    private long lasttime;
    @Transient
    private long createtime;
    @Transient
    private String gender;
    @Transient
    private String introduce;
    @Transient
    private String imagePath;
    @Transient
    private String nickname;
    private int userId;
    private int relation;
    @Transient
    private List<PhotolistBean> photolist;

    @Generated(hash = 380260547)
    public UserInfoDataBean(Long id, int userId, int relation) {
        this.id = id;
        this.userId = userId;
        this.relation = relation;
    }

    @Generated(hash = 69953430)
    public UserInfoDataBean() {
    }

    public String getArea() {
        return area;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
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

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public List<PhotolistBean> getPhotolist() {
        return photolist;
    }

    public void setPhotolist(List<PhotolistBean> photolist) {
        this.photolist = photolist;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}