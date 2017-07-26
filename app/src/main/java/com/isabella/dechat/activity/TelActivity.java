package com.isabella.dechat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.media.EMCallSurfaceView;
import com.isabella.dechat.R;
import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.util.SDCardUtils;
import com.isabella.dechat.widget.MyToast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.superrtc.sdk.VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill;


/**
 * 视频通话界面
 */
public class TelActivity extends Activity {


    /**
     * 1 拨打
     * 2 接听 电话
     */
    public int type;
    public String uid;


    @BindView(R.id.tel_text)
    TextView telActivityHint;
    @BindView(R.id.tel_accept)
    ImageView telActivityAccept;
    @BindView(R.id.tel_ring_off)
    ImageView telActivityHandup;
    @BindView(R.id.tel_dis_accept)
    ImageView telActivityDisaccept;
    @BindView(R.id.tel_user_surface)
    EMCallSurfaceView surfaceBig;
    @BindView(R.id.tel_me_surface)
    EMCallSurfaceView surfaceSmall;
    static boolean temp;
    @BindView(R.id.tel_qie_huan)
    ImageView telQieHuan;
    boolean isClick = false;
    @BindView(R.id.tel_jie_tu)
    ImageView telJieTu;
    private EMCallManager.EMVideoCallHelper callHelper;

    public static void startTelActivity(int type, String uid, Context context) {

        Intent intent = new Intent(context, TelActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("uid", uid);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_tel);
        ButterKnife.bind(this);
        temp = true;

        type = getIntent().getExtras().getInt("type");

        uid = getIntent().getExtras().getString("uid");

//        telActivityAccept 1 接听电话
        telActivityAccept.setTag(1);

        surfaceSmall.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceSmall.setZOrderOnTop(true);

        surfaceBig.setScaleMode(EMCallViewScaleModeAspectFill);
        EMClient.getInstance().callManager().setSurfaceView(surfaceSmall, surfaceBig);
        surfaceBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    EMClient.getInstance().callManager().setSurfaceView(surfaceBig, surfaceSmall);
                    isClick = true;
                }
                {
                    EMClient.getInstance().callManager().setSurfaceView(surfaceSmall, surfaceBig);
                    isClick = false;
                }
            }
        });

        if (type == 1) {
            //拨打电话
            telActivityAccept.setVisibility(View.GONE);
            telActivityDisaccept.setVisibility(View.GONE);
            telActivityHandup.setVisibility(View.VISIBLE);

            telJieTu.setVisibility(View.GONE);
            telQieHuan.setVisibility(View.GONE);
            surfaceBig.setVisibility(View.INVISIBLE);
            surfaceSmall.setVisibility(View.INVISIBLE);
            try {


                EMClient.getInstance().callManager().makeVideoCall(uid);
//                EMClient.getInstance().callManager().makeVoiceCall(uid);

                IApplication.ring();

            } catch (EMServiceNotReadyException e) {
                e.printStackTrace();
            }
        } else {
            //接听电话
            telActivityHandup.setVisibility(View.GONE);
            telActivityAccept.setVisibility(View.VISIBLE);
            telActivityDisaccept.setVisibility(View.VISIBLE);

            telJieTu.setVisibility(View.GONE);
            telQieHuan.setVisibility(View.GONE);
            surfaceBig.setVisibility(View.INVISIBLE);
            surfaceSmall.setVisibility(View.INVISIBLE);


        }

        addListener();
        telQieHuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().callManager().switchCamera();
            }
        });
        callHelper = EMClient.getInstance().callManager().getVideoCallHelper();
        telJieTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
                callHelper.takePicture(filePath);
                MyToast.getInstance().makeText("截图成功");
            }
        });
        EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(true);

    }

    public void addListener() {
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {

                System.out.println("callState = " + callState);
                System.out.println("error = " + error);

                switch (callState) {
                    case CONNECTING: // 正在连接对方


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                telActivityHint.setText("呼叫中");
                            }
                        });

                        break;
                    case CONNECTED: // 双方已经建立连接

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                telActivityHint.setText("连接中");

                            }
                        });
                        break;

                    case ACCEPTED: // 电话接通成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                telActivityHandup.setVisibility(View.VISIBLE);
                                telActivityAccept.setVisibility(View.GONE);
                                telActivityDisaccept.setVisibility(View.GONE);
                                telQieHuan.setVisibility(View.VISIBLE);
                                telJieTu.setVisibility(View.VISIBLE);
                                surfaceBig.setVisibility(View.VISIBLE);
                                surfaceSmall.setVisibility(View.VISIBLE);
                                telActivityHint.setText("通话中");

                            }
                        });
                        break;
                    case DISCONNECTED: // 电话断了


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                Toast.makeText(TelActivity.this, "通话已经结束", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if (error == CallError.ERROR_NO_DATA) {
                            //无通话数据
                        } else {

                        }
                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        break;
                    default:
                        break;
                }

            }
        });

    }


    @OnClick({R.id.tel_accept, R.id.tel_ring_off, R.id.tel_dis_accept})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tel_accept:
                try {
//                    接听电
                    int tag = (int) telActivityAccept.getTag();
                    if (tag == 1) {
                        telActivityAccept.setTag(2);
                        telActivityAccept.setVisibility(View.VISIBLE);
                        telActivityDisaccept.setVisibility(View.VISIBLE);
                        telActivityHandup.setVisibility(View.GONE);

                        telJieTu.setVisibility(View.GONE);
                        telQieHuan.setVisibility(View.GONE);
                        surfaceBig.setVisibility(View.INVISIBLE);
                        surfaceSmall.setVisibility(View.INVISIBLE);
                        EMClient.getInstance().callManager().answerCall();
                    } else {

                        EMClient.getInstance().callManager().endCall();

                        finish();

                    }

                } catch (EMNoActiveCallException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.tel_ring_off:
                //挂断电话
                try {
                    EMClient.getInstance().callManager().endCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }


                break;
            case R.id.tel_dis_accept:
                //拒绝接听
                try {
                    EMClient.getInstance().callManager().rejectCall();
                } catch (EMNoActiveCallException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    finish();

                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        surfaceBig.release();
        surfaceSmall .release();
        EMClient.getInstance().callManager().clearRtcConnection();
        EMClient.getInstance().callManager().clearRenderView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        temp = false;
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (EMNoActiveCallException e) {
            e.printStackTrace();
        } finally {
            finish();
        }

    }
}
