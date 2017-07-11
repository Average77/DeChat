package com.isabella.dechat.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * description
 * Created by 张芸艳 on 2017/7/4.
 */

public class IFragment extends Fragment {
    public IFragment() {
        // Required empty public constructor
    }
    public void toActivity(Class clazz , Bundle bundle, int requestCode){
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if(requestCode == 0){
            startActivity(intent);
        }else {
            startActivityForResult(intent,requestCode);
        }
    }

}
