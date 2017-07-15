package com.isabella.dechat.presenter;

import com.isabella.dechat.base.BasePresenter;
import com.isabella.dechat.bean.FriendBean;
import com.isabella.dechat.bean.PhotolistBean;
import com.isabella.dechat.contact.UserInfoContact;
import com.isabella.dechat.model.UserInfoModerImlp;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoContact.UserInfoView> {
    UserInfoModerImlp userInfoModerImlp;

    public UserInfoPresenter() {
        userInfoModerImlp = new UserInfoModerImlp();
    }

    public void getData(int userId) {
        userInfoModerImlp.getData(userId, new UserInfoContact.UserInfoModelImplResult() {
            @Override
            public void success(List<PhotolistBean> photolist,int rel) {
                try {
                    view.success(photolist,rel);
                } catch (Exception e) {
                    e.printStackTrace();
                   // MyToast.makeText(IApplication.getApplication(),"请求失败,请刷新重试", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void failed(Throwable e) {
                try {
                    view.failed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                   // MyToast.makeText(IApplication.getApplication(),"请求失败,请刷新重试", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public void getDataFriend(int friendId) {
        userInfoModerImlp.getFriendData(friendId, new UserInfoContact.AddFriendModelimplResult() {
            @Override
            public void successFriend(FriendBean friendBean) {
                try {
                    view.successFriend(friendBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    // MyToast.makeText(IApplication.getApplication(),"请求失败,请刷新重试", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void failedFriend(Throwable e) {
                try {
                    view.failedFriend(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    // MyToast.makeText(IApplication.getApplication(),"请求失败,请刷新重试", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
