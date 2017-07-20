package com.isabella.dechat.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.isabella.dechat.R;
import com.isabella.dechat.base.BaseActivity;
import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.LoginBean;
import com.isabella.dechat.contact.LoginContact;
import com.isabella.dechat.presenter.LoginPresenter;
import com.isabella.dechat.util.DialogUtils;
import com.isabella.dechat.util.NetUtil;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.MyToast;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginContact.LoginView, LoginPresenter> implements LoginContact.LoginView {

    @BindView(R.id.login_back)
    ImageView loginBack;
    @BindView(R.id.login2register)
    TextView login2register;
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_checkBox)
    CheckBox loginCheckBox;
    @BindView(R.id.login_login)
    Button loginLogin;
    @BindView(R.id.login2phone_login)
    TextView login2phoneLogin;
    @BindView(R.id.login_bar)
    ProgressBar loginBar;

    private LoginPresenter loginPresenter = new LoginPresenter();
    private AlertDialog.Builder builder;

    @Override
    public LoginPresenter initPresenter() {
        return loginPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPhone.setText(PreferencesUtils.getValueByKey(this, "phone", ""));
        loginPassword.setText(PreferencesUtils.getValueByKey(this, "password", ""));
        if (TextUtils.isEmpty(loginPassword.getText()) && TextUtils.isEmpty(loginPhone.getText())) {

        } else {
            loginLogin.setBackgroundResource(R.drawable.sl_bg_login);
        }
        loginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Editable etable = loginPassword.getText();
                    Selection.setSelection(etable, etable.length());//光标在末尾显示
                } else {
                    loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Editable etable = loginPassword.getText();
                    Selection.setSelection(etable, etable.length());//光标在末尾显示
                }
            }
        });
        setBackground();
        builder = DialogUtils.setDialog(this);
        RxView.clicks(loginLogin).throttleFirst(1, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (NetUtil.isNetworkAvailable(LoginActivity.this)) {
                            if (TextUtils.isEmpty(loginPhone.getText().toString())) {
                                MyToast.getInstance().makeText( getString(R.string.phone_email_not_null));
                            } else if (TextUtils.isEmpty(loginPassword.getText().toString())) {
                                MyToast.getInstance().makeText(  getString(R.string.password_not_null));
                            } else if (loginPassword.getText().toString().length() < 6) {
                                MyToast.getInstance().makeText(  "密码不能小于6位");
                            } else {
                                loginBar.setVisibility(View.VISIBLE);
                                presenter.getData(loginPhone.getText().toString(), loginPassword.getText().toString());


                            }


                        } else {
                            builder.show();
                        }
                    }
                });
    }

    private void setBackground() {
        loginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(loginPassword.getText().toString())) {
                    loginLogin.setBackgroundResource(R.drawable.sp_unfocus);
                    // loginLogin.setEnabled(false);
                } else {
                    loginLogin.setBackgroundResource(R.drawable.sl_bg_login);
                    // loginLogin.setEnabled(true);
                }
            }
        });
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || TextUtils.isEmpty(loginPhone.getText().toString())) {
                    loginLogin.setBackgroundResource(R.drawable.sp_unfocus);
                    // loginLogin.setEnabled(false);
                } else {
                    loginLogin.setBackgroundResource(R.drawable.sl_bg_login);
                    //loginLogin.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.login_back, R.id.login2register, R.id.login2phone_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
            case R.id.login2register:
                toActivity(RegisterActivity.class, null, 0);
                break;

            case R.id.login2phone_login:
                toActivity(PhoneLoginActivity.class, null, 0);
//                Snackbar.make(container, "此功能不能真正登录", Snackbar.LENGTH_SHORT)
//                        .show();
                MyToast.getInstance().makeText( "此功能不能真正登录" );
                break;
        }
    }

    @Override
    public void success(LoginBean loginBean) {
        if (loginBean.getResult_code() == 200) {

            PreferencesUtils.addConfigInfo(IApplication.getApplication(), "password", loginPassword.getText().toString());
            toActivity(MainActivity.class, null, 0);
            finish();
        } else {
            MyToast.getInstance().makeText( loginBean.getResult_message());
        }
        loginBar.setVisibility(View.GONE);
    }

    @Override
    public void failed(Throwable e) {
        Log.d("LoginActivity", "e:" + e);
        MyToast.getInstance().makeText(  "登陆失败");
        loginBar.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
