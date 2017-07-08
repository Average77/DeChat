package com.isabella.dechat.contact;

import com.isabella.dechat.bean.UploadPhotoBean;

import java.io.File;

/**
 * description
 * Created by 张芸艳 on 2017/7/7.
 */

public class UploadContact {
    public interface UploadModel{
        void getData(File file, UploadModelImplResult uploadModelImplResult);
    }
    public  interface UploadModelImplResult{
        void success(UploadPhotoBean uploadPhotoBean);
        void failed(Throwable e);
    }
    public  interface UploadView{
        void success(UploadPhotoBean uploadPhotoBean);
        void failed(Throwable e);
    }
}
