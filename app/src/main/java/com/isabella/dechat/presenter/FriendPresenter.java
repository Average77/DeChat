package com.isabella.dechat.presenter;

import com.isabella.dechat.base.BasePresenter;
import com.isabella.dechat.bean.FriendListDataBean;
import com.isabella.dechat.contact.FriendContact;
import com.isabella.dechat.model.FriendModerImlp;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class FriendPresenter extends BasePresenter<FriendContact.FriendView> {
    FriendModerImlp uploadModerImlp;

    public FriendPresenter() {
        uploadModerImlp = new FriendModerImlp();
    }

    public void getData(long currenttimer,boolean temp) {
        uploadModerImlp.getData(currenttimer,temp, new FriendContact.FriendModelImplResult() {
            @Override
            public void success(List<FriendListDataBean> listDataBeen,boolean isData) {
                view.success(listDataBeen,isData);
            }

            @Override
            public void failed(Throwable e) {
                view.failed(e);
            }
        });
    }
}
