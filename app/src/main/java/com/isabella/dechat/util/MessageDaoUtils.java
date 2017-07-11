package com.isabella.dechat.util;

import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.NearbyDataBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muhanxi on 17/7/10.
 */

public class MessageDaoUtils {


    public static void insert(final List<NearbyDataBean> list){
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {


                IApplication.getApplication().daoSession.getNearbyDataBeanDao().insertInTx(list);


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
