package com.isabella.dechat.presenter;

import com.isabella.dechat.base.BasePresenter;
import com.isabella.dechat.bean.UserInfoBean;
import com.isabella.dechat.contact.UserInfoContact;
import com.isabella.dechat.model.UserInfoModerImlp;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoContact.UserInfoView> {
    UserInfoModerImlp userInfoModerImlp;

    public UserInfoPresenter() {
        userInfoModerImlp = new UserInfoModerImlp();
    }

    public void getData(int userId) {
        userInfoModerImlp.getData(userId, new UserInfoContact.UserInfoModelImplResult() {
            @Override
            public void success(UserInfoBean loginBean) {
                view.success(loginBean);
            }

            @Override
            public void failed(Throwable e) {
                view.failed(e);
            }
        });
    }
}
