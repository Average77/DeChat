package com.isabella.dechat.base;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.isabella.dechat.dao.DaoMaster;
import com.isabella.dechat.dao.DaoSession;
import com.mob.MobApplication;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;

/**
 * description
 * Created by 张芸艳 on 2017/7/4.
 */

public class IApplication extends MobApplication {
    public static IApplication application ;
    public static boolean isLogin=false;
    public static boolean isStart=false;
    private final static String dbName = "dechat.db";
    private DaoMaster.DevOpenHelper openHelper;
    public DaoSession daoSession;

    public static boolean isLogin() {
        return isLogin;
    }

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
        openHelper = new DaoMaster.DevOpenHelper(this, dbName);
        Bugly.init(getApplicationContext(), "f2732f8b16", false);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        LeakCanary.install(this);
        Fresco.initialize(this);
        KLog.init(true);
    }
    public static IApplication getApplication(){
        if(application == null){
            application = getApplication() ;
        }
        return application;
    }
}
