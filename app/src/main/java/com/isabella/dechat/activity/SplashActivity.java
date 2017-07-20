package com.isabella.dechat.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.isabella.dechat.R;
import com.isabella.dechat.base.IActivity;
import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.util.PreferencesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends IActivity {

    @BindView(R.id.iv_heart_bg)
    ImageView ivHeartBg;
    @BindView(R.id.register_bg)
    Button registerBg;
    @BindView(R.id.login_bg)
    Button loginBg;
    boolean isExit;
    private AnimationDrawable drawable;
    String locationProvider;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Boolean isFrist = PreferencesUtils.getValueByKey(SplashActivity.this, "isFrist", false);
//            if (isFrist){
//                toActivity(RegisterActivity.class,null,0);
//            }
            if (msg.what==0) {
                ivHeartBg.setVisibility(View.INVISIBLE);
            }else if (msg.what==1){
                isExit = false;
            }else if(msg.what==2){

                toActivity(MainActivity.class,null,0);
                finish();
            }
//            registerBg.setVisibility(View.VISIBLE);
//            loginBg.setVisibility(View.VISIBLE);

        }
    };
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getLocation(this);
        if (!IApplication.isStart()){
            registerBg.setEnabled(false);
            loginBg.setEnabled(false);
            registerBg.setVisibility(View.INVISIBLE);
            loginBg.setVisibility(View.INVISIBLE);
            handler.sendEmptyMessageDelayed(2,1000);
        }else{
            registerBg.setEnabled(true);
            loginBg.setEnabled(true);
            registerBg.setVisibility(View.VISIBLE);
            loginBg.setVisibility(View.VISIBLE);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(2900);
            registerBg.startAnimation(alphaAnimation);
            loginBg.startAnimation(alphaAnimation);
        }
        drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_heart_bg);
        //imageView.setBackground(drawable);
        ivHeartBg.setBackgroundDrawable(drawable);
        drawable.start();



    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerBg.setVisibility(View.INVISIBLE);
        //loginBg.setVisibility(View.INVISIBLE);
        handler.sendEmptyMessageDelayed(0, 1400);



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
            handler.sendEmptyMessageDelayed(1, 2000);
        } else {
            //  Intent intent = new Intent(Intent.ACTION_MAIN);
            // intent.addCategory(Intent.CATEGORY_HOME);
            // startActivity(intent);
            System.exit(0);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // PreferencesUtils.addConfigInfo(this, "isFrist", true);
    }

    @OnClick({R.id.register_bg, R.id.login_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_bg:
                toActivity(RegisterActivity.class, null, 0);
                break;
            case R.id.login_bg:
              //  PreferencesUtils.getValueByKey(SplashActivity.this, "isToLogin", true);
                toActivity(LoginActivity.class, null, 0);
                break;
        }
    }

    private void getLocation(Context context) {

        //1.获取位置管理器
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是网络定位
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }

        //3.获取上次的位置，一般第一次运行，此值为null
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
        } else {
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 0, 0, mListener);
        }
    }

    private void showLocation(Location location) {
        PreferencesUtils.addConfigInfo(IApplication.getApplication(), "lat", location.getLatitude() + "");
        Log.d("SplashActivity", "location.getLatitude():" + location.getLatitude());
        PreferencesUtils.addConfigInfo(IApplication.getApplication(), "lng", location.getLongitude() + "");
    }

    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        // 如果位置发生变化，重新显示
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager!=null){
            locationManager=null;
        }
        if (mListener!=null){
            mListener=null;
        }
    }
}
