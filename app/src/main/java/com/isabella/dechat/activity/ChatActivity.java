package com.isabella.dechat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.isabella.dechat.R;
import com.isabella.dechat.adapter.ChatMsgAdapter;
import com.isabella.dechat.base.IActivity;
import com.isabella.dechat.bean.EaseEmojicon;
import com.isabella.dechat.bean.EaseEmojiconGroupEntity;
import com.isabella.dechat.speex.SpeexRecorder;
import com.isabella.dechat.util.EaseSmileUtils;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.util.SDCardUtils;
import com.isabella.dechat.widget.EaseDefaultEmojiconDatas;
import com.isabella.dechat.widget.KeyBoardHelper;
import com.isabella.dechat.widget.MyToast;
import com.isabella.dechat.widget.emojicon.EaseEmojiconMenu;
import com.isabella.dechat.widget.emojicon.EaseEmojiconMenuBase;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class ChatActivity extends IActivity implements KeyBoardHelper.OnKeyBoardStatusChangeListener {

    @BindView(R.id.chat_back)
    ImageView chatBack;
    @BindView(R.id.chat_username)
    TextView chatUsername;
    @BindView(R.id.chat_user_info)
    ImageView chatUserInfo;
    @BindView(R.id.chat_voice)
    CheckBox chatVoice;
    @BindView(R.id.chat_et)
    EditText chatEt;
    @BindView(R.id.chat_express)
    CheckBox chatExpress;
    @BindView(R.id.chat_plus)
    CheckBox chatPlus;
    @BindView(R.id.chat_btn_sendtext)
    Button chatBtnSendtext;
    @BindView(R.id.chat_bt)
    Button chatBt;
    EaseEmojiconMenuBase emojiconMenu;
    @BindView(R.id.buttom_layout_view)
    LinearLayout buttomLayoutView;
    @BindView(R.id.chat_recycler)
    RecyclerView chatRecycler;
    @BindView(R.id.chat_swipe)
    SwipeRefreshLayout chatSwipe;
    @BindView(R.id.chat_picture)
    ImageView chatPicture;
    @BindView(R.id.chat_camera)
    ImageView chatCamera;
    @BindView(R.id.chat_video)
    ImageView chatVideo;
    @BindView(R.id.chat_location)
    ImageView chatLocation;
    @BindView(R.id.chat_plus_view)
    TableLayout chatPlusView;
    private ChatMsgAdapter adapter;
    boolean btn_voice = false;
    private SpeexRecorder recorderInstance;
    private List<EaseEmojiconGroupEntity> emojiconGroupList = new ArrayList<>();
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    //chatRecycler.setSelection(chatRecycler.getBottom());
                    chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
                    break;
            }
        }
    };
    private EMMessageListener msgListener;
    private List<EMMessage> list;
    private int kh;
    private int chatId;
    private EMConversation conversation;
    private long startVoiceT;
    private String fileName;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        chatSwipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        chatSwipe.setColorSchemeResources(android.R.color.holo_blue_light);
        chatSwipe.setRefreshing(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // linearLayoutManager.setStackFromEnd(true);
        chatRecycler.setLayoutManager(linearLayoutManager);
        initEmoje(null);
        initLisitener();
        chatId = PreferencesUtils.getValueByKey(this, "chatId", 1);
        conversation = EMClient.getInstance().chatManager().getConversation(chatId + "");
        if (conversation != null) {
            if (list.size() > 0) {
                List<EMMessage> messages = conversation.loadMoreMsgFromDB(list.get(list.size() - 1).getMsgId(), 10);
                list.addAll(messages);
            }

        }
        getMessage();


        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
        //  List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);


        setRecyAdapter();
        ScrollButtom();
        setRefresh();
        getKeyHeight();


        chatEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setKeyBoardModelResize();
                if (chatExpress.isChecked() || chatPlus.isChecked()) {
                    chatExpress.setChecked(false);
                    chatPlus.setChecked(false);
                    setKeyBoardModelPan();
                } else {
                    if (buttomLayoutView.getVisibility()==View.VISIBLE) {
                        setKeyBoardModelPan();
                    } else if (chatPlusView.getVisibility()==View.VISIBLE){
                        setKeyBoardModelPan();
                    }else {
                        setKeyBoardModelResize();
                    }
                }
                chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
        editTextListen();
        chatExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setKeyBoardModelPan();
                if (!((CheckBox) v).isChecked()) {
                    //  显示键盘
                    showKeyBoard(chatEt);

                }
                if (chatVoice.isChecked()) {
                    chatVoice.setChecked(false);
                    chatBt.setVisibility(View.GONE);
                    chatEt.setVisibility(View.VISIBLE);
                    btn_voice = false;
                }
                if (((CheckBox) v).isChecked()) {

                    // 显示表情
                    hidenKeyBoard(chatEt);
                    buttomLayoutView.setVisibility(View.VISIBLE);
                    chatPlusView.setVisibility(View.GONE);
                    chatPlus.setChecked(false);

                    //  chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
                }

            }
        });
        chatBt.setOnTouchListener(new View.OnTouchListener() {

            private String filePath;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startVoiceT = System.currentTimeMillis();
                        if (!Environment.getExternalStorageDirectory().exists()) {
                            MyToast.getInstance().makeText("No SDCard");
                            return false;
                        } else {
                            filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
                            System.out.println("filePath:" + filePath);
                            File file = new File(filePath + "/");
                            System.out.println("file:" + file);

                            if (!file.exists()) {
                                file.mkdirs();
                            }

                            fileName = file + File.separator + System.currentTimeMillis() + ".spx";
                            System.out.println("保存文件名：＝＝ " + fileName);
                            recorderInstance = new SpeexRecorder(fileName, handler);
                            Thread th = new Thread(recorderInstance);
                            th.start();
                            recorderInstance.setRecording(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("ChatActivity", "startVoiceT:" + startVoiceT);
                        long endVoiceT = System.currentTimeMillis();
                        Log.d("ChatActivity", "endVoiceT:" + endVoiceT);

                        int time = (int) ((endVoiceT - startVoiceT));
                        Log.d("ChatActivity", "time:" + time);
                        if (time < 1000) {
                            MyToast.getInstance().makeText("录制时间不能小于1秒哦");
                            return false;
                        } else {
                            recorderInstance.setRecording(false);
                            System.out.println("fileName = " + new File(fileName).length());
                            EMMessage message = EMMessage.createVoiceSendMessage(fileName, time, chatId + "");
                            EMClient.getInstance().chatManager().sendMessage(message);
                            Log.d("ChatActivity", "message:" + message);
                            list.add(message);
                            chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
                            adapter.notifyDataSetChanged();
                        }
                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        MyToast.getInstance().makeText("移动取消发送");
//                        recorderInstance.stopRecoding();
//                        break;
                }
                return false;
            }
        });
        chatPlus.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                setKeyBoardModelPan();
                Log.d("ChatActivity", "((CheckBox) v).isChecked():" + ((CheckBox) v).isChecked());
                if (chatVoice.isChecked()) {
                    chatVoice.setChecked(false);
                    chatBt.setVisibility(View.GONE);
                    btn_voice = false;
                    chatEt.setVisibility(View.VISIBLE);
                }
                if (((CheckBox) v).isChecked()) {
                    hidenKeyBoard(chatEt);
                    chatPlusView.setVisibility(View.VISIBLE);
                    if (buttomLayoutView.getVisibility()!=View.GONE){
                        buttomLayoutView.setVisibility(View.GONE);
                    }

                    chatRecycler.scrollToPosition(adapter.getItemCount() - 1);


                    chatExpress.setChecked(false);


                } else {
                    chatPlusView.setVisibility(View.GONE);
                    if (buttomLayoutView.getVisibility()!=View.GONE){
                        buttomLayoutView.setVisibility(View.GONE);
                    }

                }

            }
        });
        userId = PreferencesUtils.getValueByKey(this, "userId", 1);
        setKeyBoardModelResize();
        String chatUserName = PreferencesUtils.getValueByKey(this, "chatUserName", "");
        chatUsername.setText(chatUserName);
        receive();
//        RxView.touches(chatBt).throttleFirst(1, TimeUnit.MILLISECONDS)
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<MotionEvent>() {
//                    @Override
//                    public void accept(@NonNull MotionEvent motionEvent) throws Exception {
//                       String filePath;
//                        switch (motionEvent.getAction()) {
//                            case MotionEvent.ACTION_DOWN:
//                                startVoiceT = System.currentTimeMillis();
//                                if (!Environment.getExternalStorageDirectory().exists()) {
//                                    MyToast.getInstance().makeText("No SDCard");
//                                    return;
//                                } else {
//                                    filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
//                                    System.out.println("filePath:" + filePath);
//                                    File file = new File(filePath + "/");
//                                    System.out.println("file:" + file);
//
//                                    if (!file.exists()) {
//                                        file.mkdirs();
//                                    }
//
//                                    fileName = file + File.separator + System.currentTimeMillis() + ".spx";
//                                    System.out.println("保存文件名：＝＝ " + fileName);
//                                    recorderInstance = new SpeexRecorder(fileName, handler);
//                                    Thread th = new Thread(recorderInstance);
//                                    th.start();
//                                    recorderInstance.setRecording(true);
//                                }
//                                break;
//                            case MotionEvent.ACTION_UP:
//                                Log.d("ChatActivity", "startVoiceT:" + startVoiceT);
//                                long endVoiceT = System.currentTimeMillis();
//                                Log.d("ChatActivity", "endVoiceT:" + endVoiceT);
//
//                                flag = 1;
//                                int time = (int) ((endVoiceT - startVoiceT));
//                                Log.d("ChatActivity", "time:" + time);
//                                if (time < 1000) {
//                                    MyToast.getInstance().makeText("录制时间不能小于1秒哦");
//                                    return;
//                                } else {
//                                    recorderInstance.setRecording(false);
//                                    System.out.println("fileName = " + new File(fileName).length());
//                                    EMMessage message = EMMessage.createVoiceSendMessage(fileName, time, chatId + "");
//                                    EMClient.getInstance().chatManager().sendMessage(message);
//                                    Log.d("ChatActivity", "message:" + message);
//                                    list.add(message);
//                                    chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
//                                    adapter.notifyDataSetChanged();
//                                }
//                                break;
////                    case MotionEvent.ACTION_MOVE:
////                        MyToast.getInstance().makeText("移动取消发送");
////                        recorderInstance.stopRecoding();
////                        break;
//                        }
//                    //    return false;
//
//                    }
//                });
        RxView.clicks(chatVoice).throttleFirst(1, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        setKeyBoardModelResize();
                        if (chatVoice.isChecked()) {
                            chatBt.setVisibility(View.VISIBLE);
                            btn_voice = true;
                            chatEt.setVisibility(View.GONE);
                            hidenKeyBoard(chatEt);
                            if (buttomLayoutView.getVisibility()==View.VISIBLE) {
                                buttomLayoutView.setVisibility(View.GONE);
                                chatExpress.setChecked(false);
                                chatPlus.setChecked(false);
                            }
                            if (chatPlusView.getVisibility()==View.VISIBLE) {
                                chatPlusView.setVisibility(View.GONE);
                                chatExpress.setChecked(false);
                                chatPlus.setChecked(false);
                            }
                        } else {
                            chatBt.setVisibility(View.GONE);
                            btn_voice = false;
                            chatEt.setVisibility(View.VISIBLE);
                            // showKeyBoard(chatEt);
                            showKeyBoard(chatEt);
                            //  chatEt.setFocusable(true);
                        }
                    }
                });
    }


    private void editTextListen() {
        chatEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chatBtnSendtext.setVisibility(View.VISIBLE);
                chatPlus.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    chatBtnSendtext.setVisibility(View.GONE);
                    chatPlus.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getKeyHeight() {
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);

        int keyHeight = PreferencesUtils.getValueByKey(this, "kh", 300);
        if (keyHeight == 300) {
            keyHeight = kh;
        }

        EaseEmojiconMenu.LayoutParams params = (EaseEmojiconMenu.LayoutParams) buttomLayoutView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) chatPlusView.getLayoutParams();
        //  LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) emojiconMenu.getLayoutParams();
        params.height = keyHeight;
        layoutParams.height=keyHeight;
        buttomLayoutView.setLayoutParams(params);
        chatPlusView.setLayoutParams(layoutParams);
    }

    private void setRefresh() {
        chatSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chatSwipe.setRefreshing(true);
                if (conversation != null) {
                    if (list.size() > 0) {
                        conversation.getAllMsgCount();
                        List<EMMessage> messages = conversation.loadMoreMsgFromDB(list.get(0).getMsgId(), 20);
                        list.addAll(messages);
                        Collections.sort(list, new Comparator<EMMessage>() {
                            @Override
                            public int compare(EMMessage o1, EMMessage o2) {
                                return (int) (o1.getMsgTime() - o2.getMsgTime());
                            }
                        });
                        chatSwipe.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    chatSwipe.setRefreshing(false);
                }


            }
        });
    }

    private void ScrollButtom() {
        if (list.size() > 0) {
            chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
        }
    }

    private void setRecyAdapter() {
        adapter = new ChatMsgAdapter(this, list);
        chatRecycler.setAdapter(adapter);
    }

    private void getMessage() {
        //获取此会话的所有消息
        if (conversation != null) {
            List<EMMessage> messages = conversation.getAllMessages();
            list.addAll(messages);
        }
    }

    private void initEmoje(List<EaseEmojiconGroupEntity> emojiconGroupList) {
        if (emojiconMenu == null) {


            emojiconMenu = (EaseEmojiconMenu) View.inflate(ChatActivity.this, R.layout.ease_layout_emojicon_menu, null);

            //动态修改底部view 的高度 (表情 符号 view 的高度)



            if (emojiconGroupList == null) {
                emojiconGroupList = new ArrayList<>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(R.mipmap.emoji_1, Arrays.asList(EaseDefaultEmojiconDatas.getData())));
                emojiconGroupList.add(new EaseEmojiconGroupEntity(R.mipmap.emoji_36, Arrays.asList(EaseDefaultEmojiconDatas.getDataTwo())));
                emojiconGroupList.add(new EaseEmojiconGroupEntity(R.mipmap.emoji_53, Arrays.asList(EaseDefaultEmojiconDatas.getDataThree())));
            }
            ((EaseEmojiconMenu) emojiconMenu).init(emojiconGroupList);
        }
        buttomLayoutView.addView(emojiconMenu);
    }

    private void initLisitener() {
        // emojicon menu
        emojiconMenu.setEmojiconMenuListener(new EaseEmojiconMenuBase.EaseEmojiconMenuListener() {

            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {
                if (emojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION) {
                    if (emojicon.getEmojiText() != null) {
                        chatEt.append(EaseSmileUtils.getSmiledText(ChatActivity.this, emojicon.getEmojiText()));
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                if (!TextUtils.isEmpty(chatEt.getText())) {
                    KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                    chatEt.dispatchKeyEvent(event);
                }
            }
        });

    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        int keyHeight = PreferencesUtils.getValueByKey(this, "kh", 300);
        if (keyHeight != keyBoardheight) {
            PreferencesUtils.addConfigInfo(this, "kh", keyBoardheight);
            kh = keyBoardheight;

        }
        chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {

        if ((buttomLayoutView.getVisibility() == View.VISIBLE && !chatPlus.isChecked() && !chatExpress.isChecked())) {
            buttomLayoutView.setVisibility(View.GONE);
        }
        if ((chatPlusView.getVisibility() == View.VISIBLE && !chatPlus.isChecked() && !chatExpress.isChecked())) {
            chatPlusView.setVisibility(View.GONE);
        }
        if (buttomLayoutView.getVisibility() == View.GONE) {
            chatExpress.setChecked(false);
        }
        if (chatPlusView.getVisibility() == View.GONE) {
            chatPlus.setChecked(false);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("chatTitle = onBack KEYCODE_BACK");
            if (buttomLayoutView.getVisibility()==View.VISIBLE||chatPlusView.getVisibility()==View.VISIBLE) {
                buttomLayoutView.setVisibility(View.GONE);
                chatPlusView.setVisibility(View.GONE);
                chatExpress.setChecked(false);
                chatPlus.setChecked(false);
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void setKeyBoardModelPan() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    public void setKeyBoardModelResize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    //隐藏键盘

    public void hidenKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }

    public void showKeyBoard(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);


    }


    public void setTextMessage() {
        int userId = PreferencesUtils.getValueByKey(this, "userId", 1);


        Log.d("ChatActivity", "chatId:" + chatId);
        EMMessage emMessage = EMMessage.createTxtSendMessage(chatEt.getText().toString(), chatId + "");
        EMClient.getInstance().chatManager().sendMessage(emMessage);

        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("emMessage = onSuccess ");
                // PreferencesUtils.addConfigInfo(IApplication.getApplication(), "chatError", false);
            }

            @Override
            public void onError(int i, String s) {
                //PreferencesUtils.addConfigInfo(IApplication.getApplication(), "chatError", false);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        list.add(emMessage);
        adapter.notifyDataSetChanged();
        //   chatListview.setSelection(chatListview.getBottom());
        chatRecycler.scrollToPosition(adapter.getItemCount() - 1);

    }


    public void receive() {
        //收到消息
        //收到透传消息
        //收到已读回执
        //收到已送达回执
        //消息状态变动
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                System.out.println("onMessageReceived messages = " + messages);
                if (messages != null && messages.size() != 0) {
                    Log.d("ChatActivity", "messages.size():" + messages.size());
                    if (messages.get(0).getFrom().equals(chatId + "")) {
                        list.addAll(messages);
                        handler.sendEmptyMessage(0);
                    }
                }


            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                System.out.println("onCmdMessageReceived messages = " + messages);

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
                System.out.println("onMessageRead messages = " + messages);

            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
                System.out.println("onMessageDelivered messages = " + message);

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                System.out.println("onMessageChanged messages = " + message);

            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        handler.removeCallbacksAndMessages(null);
        adapter.destory();
    }

    @OnClick({R.id.chat_back, R.id.chat_user_info, R.id.chat_btn_sendtext,R.id.chat_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chat_back:
                finish();
                break;
            case R.id.chat_user_info:
                break;
            case R.id.chat_btn_sendtext:
                setTextMessage();
                chatEt.setText("");
                break;
            case R.id.chat_video:
                TelActivity.startTelActivity(1,chatId+"",ChatActivity.this);
                toActivity(TelActivity.class,null,0);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


}
