package com.isabella.dechat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.isabella.dechat.R;
import com.isabella.dechat.base.IActivity;
import com.isabella.dechat.util.PhoneCheckUtils;
import com.isabella.dechat.widget.MyToast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PhoneLoginActivity extends IActivity {

    @BindView(R.id.phone_login_back)
    ImageView phoneLoginBack;
    @BindView(R.id.phone_login_phone)
    EditText phoneLoginPhone;
    @BindView(R.id.phone_login_core)
    EditText phoneLoginCore;
    @BindView(R.id.phone_login_obtain_core)
    Button phoneLoginObtainCore;
    @BindView(R.id.phone_login_sure)
    Button phoneLoginSure;
    private EventHandler eventHandler;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:

                    if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证成功通过
                        toActivity(MainActivity.class, null, 0);
                    } else {
                        MyToast.getInstance().makeText( "验证码错误");
                    }case 1:
                    if(msg.arg2 == SMSSDK.RESULT_COMPLETE){//发送成功的情况
                        if(msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//验证成功通过
                            toActivity(MainActivity.class,null,0);
                           // MyToast.makeText(PhoneLoginActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT);
                        }
                    }else{
                        MyToast.getInstance().makeText(  "验证码错误");
                    }
                    break;
            }
        }

        ;
    };
    private void InputType(View view) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        ButterKnife.bind(this);
        //MobSDK.init(this, "1f2fb1d01177a", "2aa7f7ec186a268bb7396edd3fd070df");

        SMSSDK.initSDK(PhoneLoginActivity.this, "1f2fb1d01177a", "2aa7f7ec186a268bb7396edd3fd070df");
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message m = Message.obtain();
                m.what = 1;
                m.arg1 = event;//event
                m.arg2 = result;//result
                handler.sendMessage(m);

            }

        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        setBackground();
    }

    private void setBackground() {
        phoneLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(phoneLoginCore.getText().toString())) {
                    phoneLoginSure.setBackgroundResource(R.drawable.sp_unfocus);
                    //  phoneLoginSure.setEnabled(false);
                } else {
                    phoneLoginSure.setBackgroundResource(R.drawable.sl_bg_login);
                    //  phoneLoginSure.setEnabled(true);
                }
            }
        });
        phoneLoginCore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(phoneLoginPhone.getText().toString())) {
                    phoneLoginSure.setBackgroundResource(R.drawable.sp_unfocus);
                    // phoneLoginSure.setEnabled(false);
                } else {
                    phoneLoginSure.setBackgroundResource(R.drawable.sl_bg_login);
                    // phoneLoginSure.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.phone_login_back, R.id.phone_login_obtain_core, R.id.phone_login_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_login_back:
             //  toActivity(LoginActivity.class,null,0);
                finish();
                break;

            case R.id.phone_login_obtain_core:
                boolean chinaPhoneLegal = PhoneCheckUtils.isChinaPhoneLegal(phoneLoginPhone.getText().toString().trim());
                if (phoneLoginPhone.getText().toString().trim().length() != 11) {
                    MyToast.getInstance().makeText( getString(R.string.phone_core));
                    return;
                }
                if (!chinaPhoneLegal) {
                    MyToast.getInstance().makeText( getString(R.string.phone_style_not_right));
                    return;
                }
                SMSSDK.getVerificationCode("86", phoneLoginPhone.getText().toString().trim());
                //  SMSSDK.submitVerificationCode("86", phoneLoginPhone.getText().toString().trim(),"1234");
                MyToast.getInstance().makeText(  "验证码已发出,请注意查收");
                phoneLoginObtainCore.setEnabled(false);
                phoneLoginObtainCore.setBackgroundResource(R.drawable.sp_unfocus);
                phoneLoginCore.requestFocus();

                setTimer();
                break;
            case R.id.phone_login_sure:
                if (TextUtils.isEmpty(phoneLoginPhone.getText().toString())) {
                    MyToast.getInstance().makeText(  getString(R.string.phone_not_null));
                } else if (TextUtils.isEmpty(phoneLoginCore.getText().toString())) {
                    MyToast.getInstance().makeText( getString(R.string.core_not_null));
                } else {
                    SMSSDK.submitVerificationCode("86", phoneLoginPhone.getText().toString(), phoneLoginCore.getText().toString());

                }
                break;
        }
    }

    public void smsCommitCommitVM(String str, String str1) {
        SMSSDK.submitVerificationCode("86", str, str1);//提交短信验证码，在监听中返回，str :手机号 str1:收到的验证码
    }

    private void setTimer() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 30 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        phoneLoginObtainCore.setText(aLong + "s重新获取");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        phoneLoginObtainCore.setBackgroundResource(R.drawable.sl_bg_login);
                        phoneLoginObtainCore.setEnabled(true);
                        phoneLoginObtainCore.setText(getString(R.string.obtain_core));

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
