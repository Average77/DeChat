package com.isabella.dechat.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.isabella.dechat.R;

/**
 * description
 * Created by 张芸艳 on 2017/5/25.
 */

public class DialogUtils {
  ;
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
                NetUtil.toSystemSetting(context);
            }
        });
        return builder;
    }
}
