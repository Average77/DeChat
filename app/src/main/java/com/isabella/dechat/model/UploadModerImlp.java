package com.isabella.dechat.model;

import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.UploadPhotoBean;
import com.isabella.dechat.contact.UploadContact;
import com.isabella.dechat.core.JNICore;
import com.isabella.dechat.core.SortUtils;
import com.isabella.dechat.network.BaseObserver;
import com.isabella.dechat.network.RetrofitManager;
import com.isabella.dechat.util.GsonUtil;
import com.isabella.dechat.util.PreferencesUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UploadModerImlp implements UploadContact.UploadModel {


    @Override
    public void getData(File file, final UploadContact.UploadModelImplResult uploadModelImplResult) {

        String[] arr = file.getAbsolutePath().split("/");

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        long ctimer = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.currenttimer", ctimer + "");
        map.put("user.picWidth", PreferencesUtils.getValueByKey(IApplication.getApplication(),"picwidth","100"));
        map.put("user.picHeight",  PreferencesUtils.getValueByKey(IApplication.getApplication(),"picheight","100"));
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);


        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("image", arr[arr.length - 1], requestFile)
                .build();

//        key = value   addFormDataPart file 00101010110 key = value
//        key = value
//
//         file 00101010110
//        key = value


        RetrofitManager.uploadPhoto(body, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                UploadPhotoBean uploadPhotoBean = GsonUtil.getInstance().fromJson(result, UploadPhotoBean.class);
                if (uploadPhotoBean.getResult_code() == 200) {
                    uploadModelImplResult.success(uploadPhotoBean);
                }

            }

            @Override
            public void onFailed(Throwable e) {
                uploadModelImplResult.failed(e);

            }
        });

    }
}
