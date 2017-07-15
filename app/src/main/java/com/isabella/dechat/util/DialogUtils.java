package com.isabella.dechat.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.isabella.dechat.R;
import com.isabella.dechat.activity.SplashActivity;
import com.isabella.dechat.base.IApplication;

/**
 * description
 * Created by 张芸艳 on 2017/5/25.
 */

public class DialogUtils {

    public static AlertDialog.Builder setDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("没有网络");
        builder.setIcon(R.drawable.dynamic_praise_foc_icon_two);
        builder.setMessage("ヾ(●´∀｀●) 亲,您陷入了没有网络的异次元,是否进入网络设置页面?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferencesUtils.addConfigInfo(IApplication.getApplication(),"isToLogin",true);
                NetUtil.toSystemSetting(context);
            }
        });
        return builder;
    }
    public static AlertDialog.Builder setDialogLogin(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("没有登录");
        builder.setIcon(R.drawable.ic_to_login);
        builder.setMessage("ヾ(●´∀｀●) 亲,您还没有登录,是否去登录或注册?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IApplication.setIsStart(true);
                PreferencesUtils.addConfigInfo(IApplication.getApplication(),"isToLogin",true);
                Intent intent=new Intent(context, SplashActivity.class);
                context.startActivity(intent);
            }
        });
        return builder;
    }

}
