package com.isabella.dechat.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isabella.dechat.R;
import com.isabella.dechat.base.IApplication;


public class MyToast {

    private   Toast mToast;
    private  TextView textView;
    private static volatile MyToast myToast = null;
    private View v;

    private MyToast() {
        mToast = new Toast(IApplication.getApplication());
        mToast.setDuration(Toast.LENGTH_SHORT);
       v = LayoutInflater.from(IApplication.getApplication()).inflate(R.layout.custom_toast, null);
        textView = (TextView) v.findViewById(R.id.toast_message);
    }

    public static MyToast getInstance() {
        if (myToast == null) {
            synchronized (MyToast.class) {
                if (myToast == null) {
                    myToast = new MyToast();
                }
            }
        }

        return myToast;

    }



    public  void makeText(CharSequence text) {
        textView.setText(text);
        mToast = new Toast(IApplication.getApplication());
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(v);
        mToast.show();

    }



}