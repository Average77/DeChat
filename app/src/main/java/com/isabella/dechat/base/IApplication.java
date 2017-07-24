package com.isabella.dechat.base;

import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.isabella.dechat.R;
import com.isabella.dechat.dao.DaoMaster;
import com.isabella.dechat.dao.DaoSession;
import com.mob.MobApplication;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;

import java.util.Iterator;
import java.util.List;


/**
 * description
 * Created by 张芸艳 on 2017/7/4.
 */

public class IApplication extends MobApplication {
    public static IApplication application;
    public static boolean isLogin = false;
    public static boolean isStart = false;
    private final static String dbName = "dechat.db";
    private DaoMaster.DevOpenHelper openHelper;
    public DaoSession daoSession;


    public static boolean isStart() {
        return isStart;
    }

    public static void setIsLogin(boolean isLogin) {
        IApplication.isLogin = isLogin;
    }

    public static void setIsStart(boolean isStart) {
        IApplication.isStart = isStart;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        buglyInit();
        greenDaoInit();
        leakcanarayInit();
        frescoInit();
        kLogInit();
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e("TAG", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);


    }

    private void buglyInit() {
        Bugly.init(getApplicationContext(), "f2732f8b16", false);
    }

    private void greenDaoInit() {
        openHelper = new DaoMaster.DevOpenHelper(this, dbName);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    private void leakcanarayInit() {
        LeakCanary.install(this);
    }

    private void frescoInit() {
        Fresco.initialize(this);
    }

    private void kLogInit() {
        KLog.init(true);
    }

    public static IApplication getApplication() {
        if (application == null) {
            application = getApplication();
        }
        return application;
    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    public static void ring(){
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        soundPool.load(application, R.raw.avchat_ring,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1, 1, 0, 0, 1);
            }
        });

    }


    public static void callTo(){
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        soundPool.load(application, R.raw.avchat_connecting,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1, 1, 0, 0, 1);
            }
        });


    }
}
