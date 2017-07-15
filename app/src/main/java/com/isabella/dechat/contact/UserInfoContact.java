package com.isabella.dechat.contact;

import com.isabella.dechat.bean.FriendBean;
import com.isabella.dechat.bean.PhotolistBean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UserInfoContact {
    public interface UserInfoModel{
        void getData(int userId, UserInfoModelImplResult userInfoModelImplResult);
        void getFriendData(int friendId, AddFriendModelimplResult addFriendModelImplResult);
    }
    public  interface UserInfoModelImplResult{
        void success(List<PhotolistBean> photolist,int rel);
        void failed(Throwable e);

    }
    public  interface UserInfoView{
        void success(List<PhotolistBean> photolist ,int rel);
        void failed(Throwable e);
        void successFriend(FriendBean friendBean);
        void failedFriend(Throwable e);
    }
   public interface  AddFriendModelimplResult{
       void successFriend(FriendBean friendBean);
       void failedFriend(Throwable e);
   }
}
