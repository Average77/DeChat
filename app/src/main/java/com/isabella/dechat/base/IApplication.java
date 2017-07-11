package com.isabella.dechat.base;

import com.isabella.dechat.dao.DaoMaster;
import com.isabella.dechat.dao.DaoSession;
import com.mob.MobApplication;

/**
 * description
 * Created by 张芸艳 on 2017/7/4.
 */

public class IApplication extends MobApplication {
    public static IApplication application ;
    private final static String dbName = "dechat.db";
    private DaoMaster.DevOpenHelper openHelper;
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        openHelper = new DaoMaster.DevOpenHelper(this, dbName);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }
    public static IApplication getApplication(){
        if(application == null){
            application = getApplication() ;
        }
        return application;
    }
}
