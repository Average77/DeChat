package com.isabella.dechat.presenter;

import com.isabella.dechat.base.BasePresenter;
import com.isabella.dechat.bean.LoginBean;
import com.isabella.dechat.contact.LoginContact;
import com.isabella.dechat.model.LoginModerImlp;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class LoginPresenter extends BasePresenter<LoginContact.LoginView> {
    LoginModerImlp registerModerImlp;

    public LoginPresenter() {
        registerModerImlp = new LoginModerImlp();
    }

    public void getData(String phone, String password) {
        registerModerImlp.getData(phone, password, new LoginContact.LoginModelImplResult() {
            @Override
            public void success(LoginBean loginBean) {
                try {
                    view.success(loginBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                try {
                    view.failed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
