package com.isabella.dechat.presenter;

import com.isabella.dechat.base.BasePresenter;
import com.isabella.dechat.bean.RegisterBean;
import com.isabella.dechat.contact.RegisterContact;
import com.isabella.dechat.model.RegisterModerImlp;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class RegisterPresenter extends BasePresenter<RegisterContact.RegisterView> {
    RegisterModerImlp registerModerImlp;

    public RegisterPresenter() {
        registerModerImlp = new RegisterModerImlp();
    }

    public void getData(String phone, String password, String gender, String area, String age, String nickname, String intro) {
        registerModerImlp.getData(phone, password, gender, area, age, nickname, intro, new RegisterContact.RegisterModelImplResult() {
            @Override
            public void success(RegisterBean registerBean) {
                view.success(registerBean);
            }

            @Override
            public void failed(Throwable e) {
                view.failed(e);
            }
        });
    }
}
