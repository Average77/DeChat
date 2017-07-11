package com.isabella.dechat.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.isabella.dechat.R;
import com.isabella.dechat.activity.FriendCircleActivity;
import com.isabella.dechat.base.IFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FreshFragment extends IFragment {

    @BindView(R.id.friend_circle_fresh)
    LinearLayout friendCircleFresh;
    @BindView(R.id.rish_scan_fresh)
    LinearLayout rishScanFresh;
    @BindView(R.id.nearby_fresh)
    LinearLayout nearbyFresh;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fresh, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({ R.id.friend_circle_fresh, R.id.rish_scan_fresh, R.id.nearby_fresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friend_circle_fresh:
               toActivity(FriendCircleActivity.class,null,0);
                break;
            case R.id.rish_scan_fresh:
                break;
            case R.id.nearby_fresh:
                break;
        }
    }
}
