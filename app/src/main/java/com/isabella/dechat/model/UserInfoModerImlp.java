package com.isabella.dechat.model;

import com.isabella.dechat.bean.UserInfoBean;
import com.isabella.dechat.contact.UserInfoContact;
import com.isabella.dechat.network.BaseObserver;
import com.isabella.dechat.network.RetrofitManager;
import com.isabella.dechat.util.GsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UserInfoModerImlp implements UserInfoContact.UserInfoModel {

    @Override
    public void getData(int userId, final UserInfoContact.UserInfoModelImplResult userInfoModelImplResult) {

        Map map = new HashMap<>();
        map.put("user.userId", userId+"");

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectUserById.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                UserInfoBean userInfoBean = GsonUtil.getInstance().fromJson(result, UserInfoBean.class);
                userInfoModelImplResult.success(userInfoBean);
            }

            @Override
            public void onFailed(Throwable e) {
                userInfoModelImplResult.failed(e);

            }
        });


    }
}
