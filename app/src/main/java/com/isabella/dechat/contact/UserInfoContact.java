package com.isabella.dechat.contact;

import com.isabella.dechat.bean.UserInfoBean;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UserInfoContact {
    public interface UserInfoModel{
        void getData(int userId, UserInfoModelImplResult userInfoModelImplResult);
    }
    public  interface UserInfoModelImplResult{
        void success(UserInfoBean userInfoBean);
        void failed(Throwable e);
    }
    public  interface UserInfoView{
        void success(UserInfoBean userInfoBean);
        void failed(Throwable e);
    }
}
