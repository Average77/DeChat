package com.isabella.dechat.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horizontalselectedviewlibrary.HorizontalselectedView;
import com.isabella.dechat.R;
import com.isabella.dechat.base.IActivity;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.MyToast;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class RegisterActivity extends IActivity{

    @BindView(R.id.login_back)
    ImageView loginBack;
    @BindView(R.id.register2login)
    TextView register2login;
    @BindView(R.id.rigister_rb_boy)
    RadioButton rigisterRbBoy;
    @BindView(R.id.rigister_rb_girl)
    RadioButton rigisterRbGirl;
    @BindView(R.id.register_rg)
    RadioGroup registerRg;
    @BindView(R.id.register_hd)
    HorizontalselectedView registerHd;
    @BindView(R.id.register_login)
    Button registerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 54; i++) {
            strings.add(i + "岁");
        }
        registerHd.setData(strings);//设置数据源

//        registerHd.setAnLeftOffset();//向左移动一个单元
//
//        registerHd.setAnRightOffset();//向右移动一个单元
        registerRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (rigisterRbBoy.isChecked() || rigisterRbGirl.isChecked()) {
                    registerLogin.setBackgroundResource(R.drawable.sl_bg_login);
                } else {
                    registerLogin.setBackgroundResource(R.drawable.sp_unfocus);
                }
            }
        });
        RxView.clicks(registerLogin).throttleFirst(1, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (rigisterRbBoy.isChecked() || rigisterRbGirl.isChecked()) {
                            getData();
                            toActivity(PhoneRegisterActivity.class, null, 0);
                        } else {
                            MyToast.makeText(RegisterActivity.this, getString(R.string.select_sex), Toast.LENGTH_SHORT);
                        }


                    }
                });

    }


    @OnClick({R.id.login_back, R.id.register2login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.register2login:
                toActivity(LoginActivity.class, null, 0);
                break;

        }
    }


    private void getData() {
        String selectedString = registerHd.getSelectedString();//获得被选中的文本
        String substring = selectedString.substring(0, selectedString.length() - 1);
        Log.d("RegisterActivity", substring);
        String gender ;
        if (rigisterRbBoy.isChecked()) {
            gender = "男";
        } else if (rigisterRbGirl.isChecked()) {
            gender = "女";
        }else{
            gender = "女";
        }
        PreferencesUtils.addConfigInfo(this,"age",substring);
        PreferencesUtils.addConfigInfo(this,"gender",gender);

    }


}
