package com.isabella.dechat.model;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.RegisterBean;
import com.isabella.dechat.contact.RegisterContact;
import com.isabella.dechat.network.BaseObserver;
import com.isabella.dechat.network.RetrofitManager;
import com.isabella.dechat.util.GsonUtil;
import com.isabella.dechat.util.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class RegisterModerImlp implements RegisterContact.RegisterModel {
    @Override
    public void getData(String phone, String password, String gender, String area, String age, String nickname, String intro, final RegisterContact.RegisterModelImplResult registerModelImplResult) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("user.phone",phone);
        map.put("user.nickname",nickname);
        map.put("user.password",password);
        map.put("user.gender",gender);
        map.put("user.area",area);
        map.put("user.age",age+"");
        map.put("user.introduce",intro);
        String lat = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lat", "");
        String lng = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lng", "");
        if ("".equals(lat) || "".equals(lng)) {
        } else {
            map.put("user.lat", lat);
            map.put("user.lng", lng);
        }
        Log.d("RegisterModerImlp", lat);
        Log.d("RegisterModerImlp", lng);
      //  System.out.println("SortUtils.getMapResult(SortUtils.sortString(map)) = " + SortUtils.getMapResult(SortUtils.sortString(map)));

       // System.out.println("sign = " + sign);
        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_add.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                Log.d("RegisterModerImlp", result);
                RegisterBean bean = GsonUtil.getInstance().fromJson(result, RegisterBean.class);
                registerModelImplResult.success(bean);
                EMClient.getInstance().login(bean.getData().getUserId()+"",bean.getData().getYxpassword(),new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });
            }

            @Override
            public void onFailed(Throwable e) {
                registerModelImplResult.failed(e);

            }
        });

    }
}
