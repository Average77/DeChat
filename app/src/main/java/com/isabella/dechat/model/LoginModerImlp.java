package com.isabella.dechat.model;

import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.LoginBean;
import com.isabella.dechat.cipher.Md5Utils;
import com.isabella.dechat.cipher.aes.JNCryptorUtils;
import com.isabella.dechat.cipher.rsa.RsaUtils;
import com.isabella.dechat.contact.LoginContact;
import com.isabella.dechat.network.BaseObserver;
import com.isabella.dechat.network.RetrofitManager;
import com.isabella.dechat.util.Constants;
import com.isabella.dechat.util.GsonUtil;
import com.isabella.dechat.util.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class LoginModerImlp implements LoginContact.LoginModel {

    @Override
    public void getData(String phone, String password, final LoginContact.LoginModelImplResult loginModelImplResult) {
        String randomKey =   RsaUtils.getStringRandom(16);


        String rsaRandomKey =   RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(),randomKey);


        String cipherPhone =   JNCryptorUtils.getInstance().encryptData(phone,IApplication.getApplication(),randomKey);


        Map map = new HashMap<>();
        map.put("user.phone",cipherPhone);
        map.put("user.password", Md5Utils.getMD5(password));
        map.put("user.secretkey",rsaRandomKey);
        map.put("user.lat", PreferencesUtils.getValueByKey(IApplication.getApplication(),"lat","34.0"));
        map.put("user.lng", PreferencesUtils.getValueByKey(IApplication.getApplication(),"lng","34.0"));
        RetrofitManager.post(Constants.LOGIN_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                LoginBean loginBean = GsonUtil.getInstance().fromJson(result, LoginBean.class);
                loginModelImplResult.success(loginBean);
            }

            @Override
            public void onFailed(Throwable e) {
                loginModelImplResult.failed(e);

            }
        });


    }
}
