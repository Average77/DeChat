package com.isabella.dechat.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.isabella.dechat.R;
import com.isabella.dechat.activity.MainActivity;
import com.isabella.dechat.activity.SplashActivity;
import com.isabella.dechat.base.AppManager;
import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.base.IFragment;
import com.isabella.dechat.network.cookie.CookiesManager;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MyFragment extends IFragment {


    @BindView(R.id.me_exit_login)
    LinearLayout meExitLogin;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.me_exit_login)
    public void onViewClicked() {
        PreferencesUtils.addConfigInfo(getActivity(),"isLogin",false);
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });
        MyToast.getInstance().makeText("退出登录成功!");
        new CookiesManager(IApplication.application).removeAllCookie();
        IApplication.setIsStart(true);
        toActivity(SplashActivity.class,null,0);
        AppManager.getAppManager().finishActivity(MainActivity.class);
    }
}
