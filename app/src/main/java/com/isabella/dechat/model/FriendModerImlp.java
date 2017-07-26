package com.isabella.dechat.model;

import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.FriendListBean;
import com.isabella.dechat.bean.FriendListDataBean;
import com.isabella.dechat.contact.FriendContact;
import com.isabella.dechat.dao.FriendListDataBeanDao;
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

public class FriendModerImlp implements FriendContact.FriendModel{


    @Override
    public void getData(long currenttimer,boolean temp, final FriendContact.FriendModelImplResult friendModelImplResult) {
        Map map = new HashMap<>();
        map.put("user.currenttimer", currenttimer+"");
        if (temp) {
            Observable.create(new ObservableOnSubscribe<List<FriendListDataBean>>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<List<FriendListDataBean>> e) throws Exception {


                    List<FriendListDataBean> photolistBean = IApplication.getApplication().daoSession.getFriendListDataBeanDao().queryBuilder()
                            .orderDesc(FriendListDataBeanDao.Properties.Id)
                            .distinct()
                            .limit(10)
                            .build().list();

                    e.onNext(photolistBean);

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<FriendListDataBean>>() {
                        @Override
                        public void accept(@NonNull List<FriendListDataBean> photolistBean) throws Exception {
                            friendModelImplResult.success(photolistBean,true);

                        }
                    });
        }

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUserAndFriend.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                if (result!=null||!"".equals("")){
                FriendListBean friendListBean = GsonUtil.getInstance().fromJson(result, FriendListBean.class);
               if (friendListBean.getResult_code()==200) {
                    final List<FriendListDataBean> date = friendListBean.getData();
                    friendModelImplResult.success(date, false);
                    Observable.create(new ObservableOnSubscribe<Long>() {
                        @Override
                        public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {


                            IApplication.getApplication().daoSession.getFriendListDataBeanDao().insertOrReplaceInTx(date);


                        }

                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(@NonNull Long aLong) throws Exception {

                                }
                            });
                }
                }


            }

            @Override
            public void onFailed(Throwable e) {

                    friendModelImplResult.failed(e);



            }
        });

    }
}
