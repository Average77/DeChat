package com.isabella.dechat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isabella.dechat.R;
import com.isabella.dechat.base.IActivity;
import com.isabella.dechat.util.GlideUtils;
import com.isabella.dechat.util.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description
 * Created by 张芸艳 on 2017/7/9.
 */

public class FriendCircleActivity extends IActivity {
    @BindView(R.id.friend_circle_back)
    ImageView friendCircleBack;
    @BindView(R.id.friend_circle_upload)
    ImageView friendCircleUpload;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.friend_circle_background)
    ImageView friendCircleBackground;
    @BindView(R.id.friend_circle_username_background)
    TextView friendCircleUsernameBackground;
    @BindView(R.id.friend_circle_username)
    TextView friendCircleUsername;
    @BindView(R.id.friend_circle_head)
    ImageView friendCircleHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle);
        ButterKnife.bind(this);
        String nickname = PreferencesUtils.getValueByKey(this, "nickname", "username");
        Boolean isSetPhoto = PreferencesUtils.getValueByKey(this, "isSetPhoto", false);
        if (isSetPhoto) {
            GlideUtils.getInstance().havaRound(PreferencesUtils.getValueByKey(this, "imagepath", ""), friendCircleHead, this);
        }
        friendCircleUsernameBackground.setText(nickname);
        friendCircleUsername.setText(nickname);
    }

    @OnClick({R.id.friend_circle_back, R.id.friend_circle_upload, R.id.friend_circle_background, R.id.friend_circle_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friend_circle_back:
                finish();
                break;
            case R.id.friend_circle_upload:
                PreferencesUtils.addConfigInfo(FriendCircleActivity.this,"from",1);
                toActivity(UploadActivity.class,null,0);
                break;
            case R.id.friend_circle_background:
                break;
            case R.id.friend_circle_head:
                break;
        }
    }
}
