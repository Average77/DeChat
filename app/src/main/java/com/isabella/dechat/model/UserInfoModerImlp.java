package com.isabella.dechat.model;

import android.util.Log;

import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.FriendBean;
import com.isabella.dechat.bean.PhotolistBean;
import com.isabella.dechat.bean.UserInfoBean;
import com.isabella.dechat.bean.UserInfoDataBean;
import com.isabella.dechat.contact.UserInfoContact;
import com.isabella.dechat.dao.PhotolistBeanDao;
import com.isabella.dechat.network.BaseObserver;
import com.isabella.dechat.network.RetrofitManager;
import com.isabella.dechat.util.GsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UserInfoModerImlp implements UserInfoContact.UserInfoModel {



    @Override
    public void getData(final int userId, final UserInfoContact.UserInfoModelImplResult userInfoModelImplResult) {
        Observable.create(new ObservableOnSubscribe<List<PhotolistBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<PhotolistBean>> e) throws Exception {


                List<PhotolistBean> photolistBean = IApplication.getApplication().daoSession.getPhotolistBeanDao().queryBuilder()
                        .where(PhotolistBeanDao.Properties.UserId.eq(userId))
                        .distinct()
                        .build().list();

                e.onNext(photolistBean);

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PhotolistBean>>() {
                    @Override
                    public void accept(@NonNull List<PhotolistBean> photolistBean) throws Exception {
                        userInfoModelImplResult.success(photolistBean,0);
                        Log.d("RecyclerModerImlp", "nearbyDataBeen:" + photolistBean);
                    }
                });
        Map map = new HashMap<>();
        map.put("user.userId", userId+"");


        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectUserById.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                UserInfoBean userInfoBean = GsonUtil.getInstance().fromJson(result, UserInfoBean.class);
                final UserInfoDataBean data = userInfoBean.getData();
                final List<PhotolistBean> photolist = userInfoBean.getData().getPhotolist();
                userInfoModelImplResult.success(photolist,userInfoBean.getData().getRelation());
                Observable.create(new ObservableOnSubscribe<Long>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {


                        IApplication.getApplication().daoSession.getPhotolistBeanDao().insertInTx(photolist);


                    }

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {

                            }
                        });
            }

            @Override
            public void onFailed(Throwable e) {
                userInfoModelImplResult.failed(e);

            }
        });


    }


    @Override
    public void getFriendData(int friendId, final UserInfoContact.AddFriendModelimplResult addFriendModelImplResult) {
        Map map = new HashMap<>();
        map.put("relationship.friendId", friendId+"");


        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_addFriends.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);

                FriendBean friendBean = GsonUtil.getInstance().parseObject(result, FriendBean.class);
                addFriendModelImplResult.successFriend(friendBean);

            }

            @Override
            public void onFailed(Throwable e) {
                addFriendModelImplResult.failedFriend(e);

            }
        });
    }
}
