package com.isabella.dechat.contact;

import com.isabella.dechat.bean.NearbyDataBean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class RecyclerContact {
    public interface RecyclerModel{
        void getData(long currentTime, RecyclerModelImplResult recyclerModelImplResult);
    }
    public  interface RecyclerModelImplResult{
        void success(List<NearbyDataBean> data);
        void failed(Throwable e);
    }
    public  interface RecyView{
        void success(List<NearbyDataBean> data);
        void failed(Throwable e);
    }
}
