package com.isabella.dechat.contact;

import com.isabella.dechat.bean.FriendListDataBean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/7/14.
 */

public class FriendContact {
    public interface FriendModel{
        void getData(long currenttimer,boolean temp, FriendModelImplResult friendModelImplResult);
    }
    public  interface FriendModelImplResult{
        void success(List<FriendListDataBean> date,boolean isData);
        void failed(Throwable e);
    }
    public  interface FriendView{
        void success(List<FriendListDataBean> date,boolean isData);
        void failed(Throwable e);
    }
}
