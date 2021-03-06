package com.isabella.dechat.network;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;



public interface ApiService {





    @GET
    public Observable<String> get(@Url String url);


    @GET
    public Observable<String> get(@Url String url, @QueryMap Map<String, String> map);

//    @FormUrlEncoded
//    @POST
//    public Observable<String> registerGetData(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    public Observable<String> post(@Url String url, @FieldMap Map<String, String> map);


    //上传图片
    @Multipart
    @POST("MyInterface/userAction_uploadImage.action")
    Observable<String> uploadPhoto(@Part("user.file") MultipartBody file, @PartMap Map<String,String> map);
    //上传相册
    @Multipart
    @POST("MyInterface/userAction_uploadPhotoAlbum.action")
    Observable<String> uploadAlbum(@Part("user.file") MultipartBody file, @PartMap Map<String,String> map);
}
