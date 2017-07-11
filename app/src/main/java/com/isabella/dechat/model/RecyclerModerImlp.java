package com.isabella.dechat.model;

import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.NearbyDataBean;
import com.isabella.dechat.bean.NearbyPeople;
import com.isabella.dechat.contact.RecyclerContact;
import com.isabella.dechat.network.BaseObserver;
import com.isabella.dechat.network.RetrofitManager;
import com.isabella.dechat.util.GsonUtil;
import com.isabella.dechat.util.MessageDaoUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class RecyclerModerImlp implements RecyclerContact.RecyclerModel {

    @Override
    public void getData( long currentTime, final RecyclerContact.RecyclerModelImplResult recyclerModelImplResult) {
        List<NearbyDataBean> nearbyDataBeen = IApplication.getApplication().daoSession.getNearbyDataBeanDao().loadAll();
       // Log.d("RecyclerModerImlp", "nearbyDataBeen:" + nearbyDataBeen);
        //  List<NearbyDataBean> userList =   IApplication.getApplication().daoSession.getNearbyDataBeanDao().queryBuilder().list();
        recyclerModelImplResult.success(nearbyDataBeen);
        Map<String,String> map = new HashMap<>();
        map.put("user.currenttimer", currentTime + "");

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result = " + result);
                NearbyPeople nearbyPeople = GsonUtil.getInstance().fromJson(result, NearbyPeople.class);
                List<NearbyDataBean> data = nearbyPeople.getData();
//                NearbyDataBean nearbyDataBean=new NearbyDataBean();
//                for (NearbyDataBean dataBean : data) {
//                    nearbyDataBean.setAge(dataBean.getAge());
//                    nearbyDataBean.setIntroduce(dataBean.getIntroduce());
//                    nearbyDataBean.setArea(dataBean.getArea());
//                    nearbyDataBean.setCreatetime(dataBean.getCreatetime());
//                    nearbyDataBean.setGender(dataBean.getGender());
//                    nearbyDataBean.setImagePath(dataBean.getImagePath());
//                    nearbyDataBean.setLat(dataBean.getLat());
//                    nearbyDataBean.setLng(dataBean.getLng());
//                    nearbyDataBean.setUserId(dataBean.getUserId());
//                    nearbyDataBean.setPassword(dataBean.getPassword());
//                    nearbyDataBean.setPhone(dataBean.getPhone());
//                    nearbyDataBean.setPicHeight(dataBean.getPicHeight());
//                    nearbyDataBean.setPicWidth(dataBean.getPicWidth());
//                    nearbyDataBean.setNickname(dataBean.getNickname());
//                    nearbyDataBean.setLasttime(dataBean.getLasttime());
//                }
//                IApplication.getApplication().daoSession.getNearbyDataBeanDao().insert(nearbyDataBean);
                MessageDaoUtils.insert(data);
                recyclerModelImplResult.success(data);
            }

            @Override
            public void onFailed(Throwable e) {
                recyclerModelImplResult.failed(e);

            }
        });


    }


}
