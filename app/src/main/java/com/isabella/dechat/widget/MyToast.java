package com.isabella.dechat.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isabella.dechat.R;
import com.isabella.dechat.base.IApplication;


public class MyToast {

    private  static Toast mToast;
    private static TextView textView;
    private static volatile MyToast myToast = null;

    private MyToast() {
        mToast = new Toast(IApplication.getApplication());
        mToast.setDuration(Toast.LENGTH_SHORT);;
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
    private MyToast(CharSequence text) {
        View v = LayoutInflater.from(IApplication.getApplication()).inflate(R.layout.custom_toast, null);
        textView = (TextView) v.findViewById(R.id.toast_message);
        textView.setText(text);

        mToast.setView(v);
    }


    public  void makeText(CharSequence text) {
        if(mToast == null){
            new MyToast(text);
        }else {
            textView.setText(text);
           // mToast.setDuration(duration);
        }
        mToast.show();

    }


}