package com.isabella.dechat.model;

import android.util.Log;

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
        map.put("user.lat", PreferencesUtils.getValueByKey(IApplication.getApplication(),"lat","34.0"));
        map.put("user.lng", PreferencesUtils.getValueByKey(IApplication.getApplication(),"lnt","34.0"));
        Log.d("RegisterModerImlp", PreferencesUtils.getValueByKey(IApplication.getApplication(), "lat", "34.0"));
        Log.d("RegisterModerImlp", PreferencesUtils.getValueByKey(IApplication.getApplication(), "lng", "34.0"));
      //  System.out.println("SortUtils.getMapResult(SortUtils.sortString(map)) = " + SortUtils.getMapResult(SortUtils.sortString(map)));

       // System.out.println("sign = " + sign);
        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_add.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                Log.d("RegisterModerImlp", result);
                RegisterBean bean = GsonUtil.getInstance().fromJson(result, RegisterBean.class);
                registerModelImplResult.success(bean);
            }

            @Override
            public void onFailed(Throwable e) {
                registerModelImplResult.failed(e);

            }
        });

    }
}
