package com.isabella.dechat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isabella.dechat.R;
import com.isabella.dechat.base.BaseActivity;
import com.isabella.dechat.bean.LoginBean;
import com.isabella.dechat.contact.LoginContact;
import com.isabella.dechat.fragment.FreshFragment;
import com.isabella.dechat.fragment.FriendFragment;
import com.isabella.dechat.fragment.MessageFragment;
import com.isabella.dechat.fragment.MyFragment;
import com.isabella.dechat.presenter.LoginPresenter;
import com.isabella.dechat.util.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<LoginContact.LoginView,LoginPresenter> implements LoginContact.LoginView{
    @BindView(R.id.container_fram_main)
    FrameLayout containerFramMain;
    @BindView(R.id.tabber_rg_main)
    RadioGroup tabberRgMain;
    @BindView(R.id.dechat)
    TextView dechat;
    @BindView(R.id.plus)
    ImageView plus;
    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<>();
    boolean isExit;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };
  LoginPresenter loginPresenter=new LoginPresenter();
    @Override
    public LoginPresenter initPresenter() {
        return loginPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        createFragment(savedInstanceState);
        switchFragment(0);
        dechat.setText("消息");

        tabberRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {

                    case R.id.tabber_rb_message:
                       dechat.setText("寻缘");
                        switchFragment(0);
                        InputType(group);
                        break;

                    case R.id.tabber_rb_friend:
                        switchFragment(1);
                        dechat.setText("好友");
                        InputType(group);
                        break;

                    case R.id.tabber_rb_fresh:
                        switchFragment(2);
                        dechat.setText("新鲜事");
                        InputType(group);
                        break;

                    case R.id.tabber_rb_me:
                        switchFragment(3);
                        dechat.setText("我的");
                        InputType(group);
                        break;

                }
            }
        });
        if (PreferencesUtils.getValueByKey(this, "isLogin", false)){
           // PreferencesUtils.addConfigInfo(IApplication.getApplication(),"isToLogin",false);
            loginPresenter.getData(PreferencesUtils.getValueByKey(this, "phone", ""),PreferencesUtils.getValueByKey(this, "password", ""));

        }
    }
    private void InputType(View view) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            //  Intent intent = new Intent(Intent.ACTION_MAIN);
            // intent.addCategory(Intent.CATEGORY_HOME);
            // startActivity(intent);
            System.exit(0);
        }
    }
    public void createFragment(Bundle savedInstanceState) {

        MessageFragment messageFragment = (MessageFragment) fragmentManager.findFragmentByTag("MessageFragment");
        FriendFragment friendFragment = (FriendFragment) fragmentManager.findFragmentByTag("FriendFragment");
        FreshFragment freshFragment = (FreshFragment) fragmentManager.findFragmentByTag("FreshFragment");
        MyFragment myFragment = (MyFragment) fragmentManager.findFragmentByTag("MyFragment");
        if (messageFragment == null) {
            messageFragment = new MessageFragment();
        }

        if (friendFragment == null) {
            friendFragment = new FriendFragment();
        }

        if (freshFragment == null) {
            freshFragment = new FreshFragment();
        }
        if (myFragment == null) {
            myFragment = new MyFragment();
        }

        fragments.add(messageFragment);
        fragments.add(friendFragment);
        fragments.add(freshFragment);
        fragments.add(myFragment);


    }


    public void switchFragment(int pos) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (!fragments.get(pos).isAdded()) {

            transaction.add(R.id.container_fram_main, fragments.get(pos), fragments.get(pos).getClass().getSimpleName());
        }

        for (int i = 0; i < fragments.size(); i++) {

            if (i == pos) {
                transaction.show(fragments.get(pos));
            } else {
                transaction.hide(fragments.get(i));
            }

        }
        transaction.commit();


    }


    @OnClick(R.id.plus)
    public void onViewClicked() {

    }

    @Override
    public void success(LoginBean loginBean) {

    }

    @Override
    public void failed(Throwable e) {

    }
}
