package com.isabella.dechat.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.isabella.dechat.R;
import com.isabella.dechat.base.BaseActivity;
import com.isabella.dechat.bean.RegisterBean;
import com.isabella.dechat.cipher.Md5Utils;
import com.isabella.dechat.contact.RegisterContact;
import com.isabella.dechat.presenter.RegisterPresenter;
import com.isabella.dechat.util.DialogUtils;
import com.isabella.dechat.util.NetUtil;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.MyToast;
import com.jakewharton.rxbinding2.view.RxView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class IntroActivity extends BaseActivity<RegisterContact.RegisterView, RegisterPresenter> implements RegisterContact.RegisterView {
    @BindView(R.id.detail_back)
    ImageView detailBack;
    @BindView(R.id.detail_nickname)
    EditText detailNickname;
    @BindView(R.id.detail_info)
    EditText detailInfo;
    @BindView(R.id.detail_area)
    TextView detailArea;
    @BindView(R.id.detail_select_area)
    TextView detailSelectArea;
    @BindView(R.id.detail_sure)
    Button detailSure;
    @BindView(R.id.detail_bar)
    ProgressBar detailBar;
    RegisterPresenter registerPresenter = new RegisterPresenter();
    private AlertDialog.Builder builder;

    @Override
    public RegisterPresenter initPresenter() {
        return registerPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        setBackground();
        builder = DialogUtils.setDialog(this);
        RxView.clicks(detailSure).throttleFirst(1, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (NetUtil.isNetworkAvailable(IntroActivity.this)) {
                            if (TextUtils.isEmpty(detailNickname.getText().toString())) {

                                MyToast.makeText(IntroActivity.this, getString(R.string.nickname_not_null), Toast.LENGTH_SHORT);
                            } else if (TextUtils.isEmpty(detailInfo.getText().toString())) {
                                MyToast.makeText(IntroActivity.this, getString(R.string.intro_not_null), Toast.LENGTH_SHORT);
                            } else if (getString(R.string.select_area).equals(detailSelectArea.getText().toString())) {
                                MyToast.makeText(IntroActivity.this, getString(R.string.area_not_null), Toast.LENGTH_SHORT);
                            } else {
                                detailBar.setVisibility(View.VISIBLE);
                                registerPresenter.getData(PreferencesUtils.getValueByKey(IntroActivity.this, "phone", ""), Md5Utils.getMD5(PreferencesUtils.getValueByKey(IntroActivity.this, "password", "")),
                                        PreferencesUtils.getValueByKey(IntroActivity.this, "gender", ""), detailSelectArea.getText().toString().trim(), PreferencesUtils.getValueByKey(IntroActivity.this, "age", ""),
                                        detailNickname.getText().toString().trim(), detailInfo.getText().toString().trim());


                            }
                        } else {
                            builder.show();
                        }
                        // System.out.println("o = " + o);

                    }
                });
    }

    @OnClick({R.id.detail_back, R.id.detail_select_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_back:
              //  toActivity(PhoneRegisterActivity.class, null, 0);
                finish();
                break;
            case R.id.detail_select_area:
                InputType(view);
                break;
        }
    }

    private void InputType(View view) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress();//调用CityPicker选取区域
        }
    }

    private void setBackground() {
        detailNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(detailInfo.getText().toString()) || getString(R.string.select_area).equals(detailSelectArea.getText().toString())) {
                    detailSure.setBackgroundResource(R.drawable.sp_unfocus);
                    // loginLogin.setEnabled(false);
                } else {
                    detailSure.setBackgroundResource(R.drawable.sl_bg_login);
                    // loginLogin.setEnabled(true);
                }
            }
        });
        detailInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(detailNickname.getText().toString()) || getString(R.string.select_area).equals(detailSelectArea.getText().toString())) {
                    detailSure.setBackgroundResource(R.drawable.sp_unfocus);
                    // loginLogin.setEnabled(false);
                } else {
                    detailSure.setBackgroundResource(R.drawable.sl_bg_login);
                    //loginLogin.setEnabled(true);
                }
            }
        });
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                // .titleTextColor("#696969")
                .confirTextColor("#79C3C7")
                .cancelTextColor("#696969")
                .province("北京市")
                .city("北京市")
                .district("海淀区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                detailSelectArea.setText(province.trim() + " " + city.trim() + " " + district.trim());
                detailSure.setBackgroundResource(R.drawable.sl_bg_login);
            }
        });

    }
    @Override
    public void success(RegisterBean registerBean) {
        if (registerBean.getResult_code()==200) {
            detailBar.setVisibility(View.GONE);
            PreferencesUtils.addConfigInfo(this,"from",0);
            toActivity(UploadActivity.class,null,0);
            PreferencesUtils.addConfigInfo(this, "nickname", registerBean.getData().getNickname());
        }else{
            MyToast.makeText(this,registerBean.getResult_message(),Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void failed(Throwable e) {
        detailBar.setVisibility(View.GONE);
        Log.d("IntroActivity", "e:" + e);
        MyToast.makeText(this, "注册失败", Toast.LENGTH_SHORT);

    }
}
