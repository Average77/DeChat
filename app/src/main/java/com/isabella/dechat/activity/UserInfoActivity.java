package com.isabella.dechat.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.isabella.dechat.R;
import com.isabella.dechat.adapter.UserInfoRecyAdapter;
import com.isabella.dechat.base.BaseActivity;
import com.isabella.dechat.bean.FriendBean;
import com.isabella.dechat.bean.PhotolistBean;
import com.isabella.dechat.contact.UserInfoContact;
import com.isabella.dechat.presenter.UserInfoPresenter;
import com.isabella.dechat.util.DeviceUtils;
import com.isabella.dechat.util.DialogUtils;
import com.isabella.dechat.util.GlideUtils;
import com.isabella.dechat.util.NetUtil;
import com.isabella.dechat.util.PicShowDialog;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.MyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity<UserInfoContact.UserInfoView, UserInfoPresenter> implements UserInfoContact.UserInfoView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.user_info_head)
    ImageView userInfoHead;
    @BindView(R.id.user_info_photo)
    RecyclerView userInfoPhoto;
    @BindView(R.id.user_info_lasttime)
    TextView userInfoLasttime;
    @BindView(R.id.user_info_nickname)
    TextView userInfoNickname;
    @BindView(R.id.user_info_sex_icon)
    ImageView userInfoSexIcon;
    @BindView(R.id.user_info_sex)
    TextView userInfoSex;
    @BindView(R.id.user_info_address)
    TextView userInfoAddress;
    @BindView(R.id.user_info_intro)
    TextView userInfoIntro;
    UserInfoPresenter presenter = new UserInfoPresenter();
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.user_info_age)
    TextView userInfoAge;
    List<PhotolistBean> photolist = new ArrayList<>();
    @BindView(R.id.user_info_floating)
    FloatingActionButton userInfoFloating;
    @BindView(R.id.user_info_msg)
    Button userInfoMsg;
    private int imageWidth;
    private UserInfoRecyAdapter userInfoRecyAdapter;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder dialogLogin;
    private int userId;
    private AlertDialog.Builder dialogAddFriend;

    @Override
    public UserInfoPresenter initPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //完全隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        builder = DialogUtils.setDialog(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        // Bundle bundle = intent.getExtras();
        // NearbyDataBean nearbyDataBean= (NearbyDataBean) bundle.getSerializable("userInfo");
        userId = intent.getIntExtra("userId", 0);
        //int userId = nearbyDataBean.getUserId();
        int picWidth = intent.getIntExtra("picWidth", 100);
        int picHeight = intent.getIntExtra("picHeight", 100);
        int age = intent.getIntExtra("age", 18);
        String nickname = intent.getStringExtra("nickname");
        String gender = intent.getStringExtra("sex");
        String address = intent.getStringExtra("address");
        String intro = intent.getStringExtra("intro");
        String imagePath = intent.getStringExtra("imagePath");
        long lasttime = intent.getLongExtra("lasttime", System.currentTimeMillis());
        presenter.getData(userId);
        collapsingToolbarLayout.setTitle(nickname);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageWidth = DeviceUtils.getDisplayInfomation(this).x;
        // Random random = new Random();
        //int i = random.nextInt(30) + 14;
        userInfoAge.setText(age + "岁");
        userInfoNickname.setText(nickname);
        if ("男".equals(gender)) {
            userInfoSexIcon.setImageResource(R.mipmap.ic_sex_boy);
        } else {
            userInfoSexIcon.setImageResource(R.mipmap.ic_sex_gril);
        }
        dialogAddFriend = setDialogAddFriend();
        userInfoSex.setText(gender);
        userInfoAddress.setText(address);
        userInfoIntro.setText(intro);
        dialogLogin = DialogUtils.setDialogLogin(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(lasttime);
        userInfoLasttime.setText(format);
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) userInfoHead.getLayoutParams();
//        CoordinatorLayout.LayoutParams bar= (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
//        float scale = (float) imageWidth / (float) picWidth;
//        params.width = imageWidth;
//        params.height = (int) ((float) scale * (float)picHeight);
//        userInfoHead.setLayoutParams(params);
//        bar.width = imageWidth;
//        bar.height = (int) ((float) scale * (float)picHeight);
//        appbar.setLayoutParams(bar);
        GlideUtils.getInstance().photo(imagePath, userInfoHead, this);
        userInfoRecyAdapter = new UserInfoRecyAdapter(photolist, this);
        userInfoPhoto.setAdapter(userInfoRecyAdapter);
        userInfoRecyAdapter.setOnItemClickListener(new UserInfoRecyAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                PicShowDialog dialog = new PicShowDialog(UserInfoActivity.this, photolist, postion);
                dialog.show();
            }
        });
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        userInfoPhoto.setLayoutManager(linearLayoutManager);
    }

    public AlertDialog.Builder setDialogAddFriend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加好友");
        builder.setIcon(R.drawable.plum_flower);
        builder.setMessage("ヾ(●´∀｀●) 亲,是否要添加对方为好友?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.getDataFriend(userId);
            }
        });
        return builder;
    }

    @Override
    public void success(List<PhotolistBean> photo, int rel) {
        photolist.clear();
        if (PreferencesUtils.getValueByKey(UserInfoActivity.this, "isLogin", false)) {
            if (rel == 0) {
                userInfoFloating.setVisibility(View.VISIBLE);
                userInfoMsg.setVisibility(View.GONE);
            } else {
                userInfoFloating.setVisibility(View.GONE);
                userInfoMsg.setVisibility(View.VISIBLE);
            }
        }
        if (photo != null || photo.size() != 0) {
            photolist.addAll(photo);
            userInfoRecyAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void successFriend(FriendBean friendBean) {
        MyToast.getInstance().makeText(friendBean.getResult_message());
        if (friendBean.getResult_code() == 200) {
            userInfoFloating.setVisibility(View.GONE);
            userInfoMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void failed(Throwable e) {


    }

    @Override
    public void failedFriend(Throwable e) {


    }

    @OnClick({R.id.user_info_msg, R.id.user_info_floating})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_info_msg:
                if (NetUtil.isNetworkAvailable(this)) {
                    if (!PreferencesUtils.getValueByKey(UserInfoActivity.this, "isLogin", false)) {
                        dialogLogin.show();
                    } else {


                    }
                } else {
                    builder.show();
                }

                break;
            case R.id.user_info_floating:
                if (NetUtil.isNetworkAvailable(this)) {
                    if (!PreferencesUtils.getValueByKey(UserInfoActivity.this, "isLogin", false)) {
                        dialogLogin.show();
                    } else {
                        dialogAddFriend.show();

                    }
                } else {
                    builder.show();
                }
                break;
        }
    }

    @OnClick(R.id.user_info_msg)
    public void onViewClicked() {
        toActivity(MessageActivity.class,null,0);
    }
}
