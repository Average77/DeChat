package com.isabella.dechat.presenter;

import com.isabella.dechat.base.BasePresenter;
import com.isabella.dechat.bean.UploadPhotoBean;
import com.isabella.dechat.contact.UploadContact;
import com.isabella.dechat.model.UploadModerImlp;

import java.io.File;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UploadPresenter extends BasePresenter<UploadContact.UploadView> {
    UploadModerImlp uploadModerImlp;

    public UploadPresenter() {
        uploadModerImlp = new UploadModerImlp();
    }

    public void getData(File file) {
        uploadModerImlp.getData(file, new UploadContact.UploadModelImplResult() {
            @Override
            public void success(UploadPhotoBean uploadPhotoBean) {
                try {
                    view.success(uploadPhotoBean);
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
