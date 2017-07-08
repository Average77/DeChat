package com.isabella.dechat.contact;

import com.isabella.dechat.bean.RegisterBean;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class RegisterContact {
    public interface RegisterModel{
        void getData(String phone,String password,String gender,String area,String age,String nickname,String intro,RegisterModelImplResult registerModelImplResult);
    }
    public  interface RegisterModelImplResult{
        void success(RegisterBean registerBean);
        void failed(Throwable e);
    }
    public  interface RegisterView{
        void success(RegisterBean registerBean);
        void failed(Throwable e);
    }
}
