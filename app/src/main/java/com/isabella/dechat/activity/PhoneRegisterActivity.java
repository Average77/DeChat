package com.isabella.dechat.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.isabella.dechat.R;
import com.isabella.dechat.base.IActivity;
import com.isabella.dechat.util.DialogUtils;
import com.isabella.dechat.util.NetUtil;
import com.isabella.dechat.util.PhoneCheckUtils;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.MyToast;
import com.jakewharton.rxbinding2.view.RxView;

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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * description
 * Created by 张芸艳 on 2017/7/6.
 */

public class PhoneRegisterActivity extends IActivity {
    @BindView(R.id.phone_register_back)
    ImageView phoneRegisterBack;
    @BindView(R.id.phone_register_phone)
    EditText phoneRegisterPhone;
    @BindView(R.id.phone_register_core)
    EditText phoneRegisterCore;
    @BindView(R.id.phone_register_obtain_core)
    Button phoneRegisterObtainCore;
    @BindView(R.id.phone_register_password)
    EditText phoneRegisterPassword;
    @BindView(R.id.phone_register_checkBox)
    CheckBox phoneRegisterCheckBox;
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
                        MyToast.makeText(PhoneRegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT);
                    }
                case 1:
                    if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {//发送成功的情况
                        if (msg.arg1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证成功通过
                            //toActivity(IntroActivity.class, null, 0);
                            MyToast.makeText(PhoneRegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT);
                        }
                    } else {
                        MyToast.makeText(PhoneRegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT);
                    }
                    break;
            }
        }

        ;
    };
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_phone_register);
        ButterKnife.bind(this);
        //MobSDK.init(this, "1f2fb1d01177a", "2aa7f7ec186a268bb7396edd3fd070df");
        SMSSDK.initSDK(PhoneRegisterActivity.this, "1f2fb1d01177a", "2aa7f7ec186a268bb7396edd3fd070df");
        phoneRegisterPhone.setText(PreferencesUtils.getValueByKey(this, "phone", ""));
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
        builder = DialogUtils.setDialog(this);
        setBackground();
        RxView.clicks(phoneLoginSure).throttleFirst(1, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (TextUtils.isEmpty(phoneRegisterPhone.getText().toString())) {
                            MyToast.makeText(PhoneRegisterActivity.this, getString(R.string.phone_not_null), Toast.LENGTH_SHORT);
                        } else if (TextUtils.isEmpty(phoneRegisterCore.getText().toString())) {
                            MyToast.makeText(PhoneRegisterActivity.this, getString(R.string.core_not_null), Toast.LENGTH_SHORT);
                        } else if (TextUtils.isEmpty(phoneRegisterPassword.getText().toString())) {
                            MyToast.makeText(PhoneRegisterActivity.this, getString(R.string.password_not_null), Toast.LENGTH_SHORT);
                        } else if(phoneRegisterCore.length()<4){
                            MyToast.makeText(PhoneRegisterActivity.this, "验证码是4位哦", Toast.LENGTH_SHORT);
                        }else {
                            PreferencesUtils.addConfigInfo(PhoneRegisterActivity.this, "phone", phoneRegisterPhone.getText().toString().trim());
                            PreferencesUtils.addConfigInfo(PhoneRegisterActivity.this, "password", phoneRegisterPassword.getText().toString().trim());
                            // SMSSDK.submitVerificationCode("86", phoneRegisterPhone.getText().toString(), phoneRegisterCore.getText().toString());
                            toActivity(IntroActivity.class, null, 0);
                        }


                    }
                });
        RxView.clicks(phoneRegisterObtainCore).throttleFirst(1, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (NetUtil.isNetworkAvailable(PhoneRegisterActivity.this)) {
                            boolean chinaPhoneLegal = PhoneCheckUtils.isChinaPhoneLegal(phoneRegisterPhone.getText().toString().trim());
                            if (phoneRegisterPhone.getText().toString().trim().length() != 11) {
                                MyToast.makeText(PhoneRegisterActivity.this, getString(R.string.phone_core), Toast.LENGTH_SHORT);
                                return;
                            }
                            if (!chinaPhoneLegal) {
                                MyToast.makeText(PhoneRegisterActivity.this, getString(R.string.phone_style_not_right), Toast.LENGTH_SHORT);
                                return;
                            }
                            SMSSDK.getVerificationCode("86", phoneRegisterPhone.getText().toString().trim());
                            //  SMSSDK.submitVerificationCode("86", phoneLoginPhone.getText().toString().trim(),"1234");

                            phoneRegisterObtainCore.setEnabled(false);
                            phoneRegisterObtainCore.setBackgroundResource(R.drawable.sp_unfocus);
                            phoneRegisterCore.requestFocus();
                            MyToast.makeText(PhoneRegisterActivity.this, "验证码已发出,请注意查收", Toast.LENGTH_SHORT);
                            setTimer();
                        } else {
                            builder.show();
                        }
                    }


                });


    }

    private void setBackground() {
        phoneRegisterPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(phoneRegisterCore.getText().toString()) || TextUtils.isEmpty(phoneRegisterPassword.getText().toString())) {
                    phoneLoginSure.setBackgroundResource(R.drawable.sp_unfocus);
                    //  phoneLoginSure.setEnabled(false);
                } else {
                    phoneLoginSure.setBackgroundResource(R.drawable.sl_bg_login);
                    //  phoneLoginSure.setEnabled(true);
                }
            }
        });
        phoneRegisterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    phoneRegisterPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Editable etable = phoneRegisterPassword.getText();
                    Selection.setSelection(etable, etable.length());//光标在末尾显示
                } else {
                    phoneRegisterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Editable etable = phoneRegisterPassword.getText();
                    Selection.setSelection(etable, etable.length());//光标在末尾显示
                }
            }
        });
        phoneRegisterCore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(phoneRegisterPassword.getText().toString()) || TextUtils.isEmpty(phoneRegisterPhone.getText().toString())) {
                    phoneLoginSure.setBackgroundResource(R.drawable.sp_unfocus);
                    // phoneLoginSure.setEnabled(false);
                } else {
                    phoneLoginSure.setBackgroundResource(R.drawable.sl_bg_login);
                    // phoneLoginSure.setEnabled(true);
                }
            }
        });
        phoneRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(phoneRegisterCore.getText().toString()) || TextUtils.isEmpty(phoneRegisterPhone.getText().toString())) {
                    phoneLoginSure.setBackgroundResource(R.drawable.sp_unfocus);
                    // phoneLoginSure.setEnabled(false);
                } else {
                    phoneLoginSure.setBackgroundResource(R.drawable.sl_bg_login);
                    // phoneLoginSure.setEnabled(true);
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @OnClick({R.id.phone_register_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_register_back:
                toActivity(RegisterActivity.class, null, 0);
                finish();
                break;
        }
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
                        phoneRegisterObtainCore.setText(aLong + "s重新获取");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        phoneRegisterObtainCore.setBackgroundResource(R.drawable.sl_bg_login);
                        phoneRegisterObtainCore.setEnabled(true);
                        phoneRegisterObtainCore.setText(getString(R.string.obtain_core));

                    }
                });

    }
}
