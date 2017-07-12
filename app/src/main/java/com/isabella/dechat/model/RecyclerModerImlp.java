package com.isabella.dechat.model;

import android.util.Log;

import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.NearbyDataBean;
import com.isabella.dechat.bean.NearbyPeople;
import com.isabella.dechat.contact.RecyclerContact;
import com.isabella.dechat.dao.NearbyDataBeanDao;
import com.isabella.dechat.network.BaseObserver;
import com.isabella.dechat.network.RetrofitManager;
import com.isabella.dechat.util.GsonUtil;
import com.isabella.dechat.util.MessageDaoUtils;
import com.isabella.dechat.util.PreferencesUtils;

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

public class RecyclerModerImlp implements RecyclerContact.RecyclerModel {
    boolean temp = false;

    @Override
    public void getData(final long currentTime, int page, final RecyclerContact.RecyclerModelImplResult recyclerModelImplResult) {
//        List<NearbyDataBean> nearbyDataBeen = IApplication.getApplication().daoSession.getNearbyDataBeanDao().loadAll();
//        recyclerModelImplResult.success(nearbyDataBeen);
//        if (temp) {
        // List<NearbyDataBean> nearbyDataBeen = IApplication.getApplication().daoSession.getNearbyDataBeanDao().loadAll();
        if (page == 1) {
            Observable.create(new ObservableOnSubscribe<List<NearbyDataBean>>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<List<NearbyDataBean>> e) throws Exception {


                    List<NearbyDataBean> nearbyDataBeen = IApplication.getApplication().daoSession.getNearbyDataBeanDao().queryBuilder()
                            .orderDesc(NearbyDataBeanDao.Properties.Id)
                            .limit(20)
                            .build().list();

                   e.onNext(nearbyDataBeen);

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<NearbyDataBean>>() {
                        @Override
                        public void accept(@NonNull List<NearbyDataBean> nearbyDataBeen) throws Exception {
                            recyclerModelImplResult.success(nearbyDataBeen, true);
                            Log.d("RecyclerModerImlp", "nearbyDataBeen:" + nearbyDataBeen);
                        }
                    });
        }
//        }
        Map<String, String> map = new HashMap<>();
        map.put("user.currenttimer", currentTime + "");

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                NearbyPeople nearbyPeople = GsonUtil.getInstance().fromJson(result, NearbyPeople.class);
                List<NearbyDataBean> data = nearbyPeople.getData();
                int userId = PreferencesUtils.getValueByKey(IApplication.getApplication(), "userId", 0);

                if (userId != 0) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getUserId() == userId) {
                            data.remove(i);
                        }
                    }
                }
                MessageDaoUtils.insert(data);
                //  PreferencesUtils.addConfigInfo(IApplication.getApplication(),"isData",true);
                temp = true;
                recyclerModelImplResult.success(data, false);

            }

            @Override
            public void onFailed(Throwable e) {
                recyclerModelImplResult.failed(e);

            }
        });


    }


}
