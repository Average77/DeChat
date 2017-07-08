package com.isabella.dechat.contact;

import com.isabella.dechat.bean.LoginBean;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class LoginContact {
    public interface LoginModel{
        void getData(String phone, String password, LoginModelImplResult loginModelImplResult);
    }
    public  interface LoginModelImplResult{
        void success(LoginBean loginBean);
        void failed(Throwable e);
    }
    public  interface LoginView{
        void success(LoginBean loginBean);
        void failed(Throwable e);
    }
}
