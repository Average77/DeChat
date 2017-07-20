package com.isabella.dechat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.isabella.dechat.R;
import com.isabella.dechat.adapter.ChatAdapter;
import com.isabella.dechat.base.IActivity;
import com.isabella.dechat.bean.EaseEmojicon;
import com.isabella.dechat.bean.EaseEmojiconGroupEntity;
import com.isabella.dechat.util.EaseSmileUtils;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.EaseDefaultEmojiconDatas;
import com.isabella.dechat.widget.KeyBoardHelper;
import com.isabella.dechat.widget.emojicon.EaseEmojiconMenu;
import com.isabella.dechat.widget.emojicon.EaseEmojiconMenuBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends IActivity implements KeyBoardHelper.OnKeyBoardStatusChangeListener {

    @BindView(R.id.chat_back)
    ImageView chatBack;
    @BindView(R.id.chat_username)
    TextView chatUsername;
    @BindView(R.id.chat_user_info)
    ImageView chatUserInfo;
    @BindView(R.id.chat_listview)
    ListView chatListview;
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
    private ChatAdapter adapter;

    private List<EaseEmojiconGroupEntity> emojiconGroupList = new ArrayList<>();
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    chatListview.setSelection(chatListview.getBottom());
                    break;
            }
        }
    };
    private EMMessageListener msgListener;
    private List<EMMessage> list;
    private int kh;
    private int chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        buttomLayoutView.setTag(2);
        adapter = new ChatAdapter(this, list);
        chatListview.setAdapter(adapter);
        initEmoje(null);
        initLisitener();


        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);

        int keyHeight = PreferencesUtils.getValueByKey(this, "kh", 300);
        if (keyHeight == 300) {
            keyHeight = kh;
        }

        EaseEmojiconMenu.LayoutParams params = (EaseEmojiconMenu.LayoutParams) buttomLayoutView.getLayoutParams();
        //  LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) emojiconMenu.getLayoutParams();
        params.height = keyHeight;
        buttomLayoutView.setLayoutParams(params);
        chatEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatExpress.isChecked() || chatPlus.isChecked()) {
                    chatExpress.setChecked(false);
                    chatPlus.setChecked(false);
                    setKeyBoardModelPan();
                } else {
                    setKeyBoardModelResize();
                }
            }
        });

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
        chatExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setKeyBoardModelPan();
                if (chatVoice.isChecked()) {
                    chatVoice.setChecked(false);
                    chatBt.setVisibility(View.GONE);
                    chatEt.setVisibility(View.VISIBLE);
                }
                if (((CheckBox) v).isChecked()) {

                    // 显示表情

                    buttomLayoutView.setVisibility(View.VISIBLE);
                    buttomLayoutView.setTag(1);
                    chatPlus.setChecked(false);
                    hidenKeyBoard(chatEt);
                } else {
                    //  显示键盘
                    showKeyBoard(chatEt);
                    //  buttomLayoutView.setVisibility(View.GONE);

                }
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
                    chatEt.setVisibility(View.VISIBLE);
                }
                if (((CheckBox) v).isChecked()) {

                    buttomLayoutView.setVisibility(View.VISIBLE);
                    buttomLayoutView.setTag(1);
                    hidenKeyBoard(chatEt);
                    chatExpress.setChecked(false);


                } else {
                    buttomLayoutView.setVisibility(View.GONE);
                    buttomLayoutView.setTag(2);

                }

            }
        });
        setKeyBoardModelResize();
        String chatUserName = PreferencesUtils.getValueByKey(this, "chatUserName", "");
        chatUsername.setText(chatUserName);
        receive();
    }

    private void initEmoje(List<EaseEmojiconGroupEntity> emojiconGroupList) {
        if (emojiconMenu == null) {


            emojiconMenu = (EaseEmojiconMenu) View.inflate(ChatActivity.this, R.layout.ease_layout_emojicon_menu, null);

            //动态修改底部view 的高度 (表情 符号 view 的高度)


            //  buttomLayoutView.setVisibility(View.GONE);

            if (emojiconGroupList == null) {
                emojiconGroupList = new ArrayList<>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(R.mipmap.emoji_1, Arrays.asList(EaseDefaultEmojiconDatas.getData())));
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
//        chatEt.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK ) {
//                    setKeyBoardModelResize();
//                    //键盘
//                    activityChatTwo.setTag(1);
//                    activityChatView.setVisibility(View.GONE);
//                    activityChatTwo.setBackgroundResource(R.drawable.ease_chatting_setmode_xiaolian_btn);
//
//                    hidenKeyBoard();
//                    if (keyBoardheight ==0 && activityChatView.getVisibility() == View.GONE){
//                        return  false;
//                    }
//                }
//                if (keyCode == KeyEvent.KEYCODE_DEL){
//                    return false;
//                }
//
//                return true;
//            }
//        });
    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        int keyHeight = PreferencesUtils.getValueByKey(this, "kh", 300);
        if (keyHeight != keyBoardheight) {
            PreferencesUtils.addConfigInfo(this, "kh", keyBoardheight);
            kh = keyBoardheight;
        }
//        if ((tag == 1 && !chatPlus.isChecked()) || (tag == 1 && !chatExpress.isChecked())) {
//            buttomLayoutView.setVisibility(View.GONE);
//            buttomLayoutView.setTag(2);
//
//        }

        //  handler.sendEmptyMessageAtTime(1, 200);
        //   chatPlus.setChecked(false);

    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
        int tag = (int) buttomLayoutView.getTag();
        if ((tag == 1 && !chatPlus.isChecked() && !chatExpress.isChecked())) {
            buttomLayoutView.setVisibility(View.GONE);
            buttomLayoutView.setTag(2);
        }

        if (tag == 2) {
            chatExpress.setChecked(false);
            chatPlus.setChecked(false);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("chatTitle = onBack KEYCODE_BACK");
            if ((int) buttomLayoutView.getTag() == 1) {
                buttomLayoutView.setVisibility(View.GONE);
                buttomLayoutView.setTag(2);
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
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

    }


    public void setTextMessage() {
        // int userId = PreferencesUtils.getValueByKey(this, "userId", 1);

        chatId = PreferencesUtils.getValueByKey(this, "chatId", 1);
        Log.d("ChatActivity", "chatId:" + chatId);
        EMMessage emMessage = EMMessage.createTxtSendMessage(chatEt.getText().toString(), chatId + "");
        EMClient.getInstance().chatManager().sendMessage(emMessage);

        emMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("emMessage = onSuccess ");
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        list.add(emMessage);
        adapter.notifyDataSetChanged();
        chatListview.setSelection(chatListview.getBottom());

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
                //  if (Integer.parseInt(messages.get(0).getFrom())==chatId){
                //if (messages.size()!=0&&messages!=null){
                //  int integer = Integer.valueOf(list.get(0).getFrom());
                //  int chatId = PreferencesUtils.getValueByKey(IApplication.getApplication(), "chatId", 1);
                //    Log.d("ChatAdapter", "integer:" + integer);
                //  Log.d("ChatAdapter", "chatId:" + chatId);
                // if (integer==chatId) {
                list.addAll(messages);
                handler.sendEmptyMessage(0);
                // }
                // }

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

    }

    @OnClick({R.id.chat_back, R.id.chat_user_info, R.id.chat_voice, R.id.chat_btn_sendtext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chat_back:
                finish();
                break;
            case R.id.chat_user_info:
                break;
            case R.id.chat_voice:

                setKeyBoardModelResize();
                if (chatVoice.isChecked()) {
                    chatBt.setVisibility(View.VISIBLE);
                    chatEt.setVisibility(View.GONE);
                    hidenKeyBoard(chatEt);
                    if ((int) buttomLayoutView.getTag() == 1) {
                        buttomLayoutView.setVisibility(View.GONE);
                        buttomLayoutView.setTag(2);
                        chatExpress.setChecked(false);
                        chatPlus.setChecked(false);
                    }
                } else {
                    chatBt.setVisibility(View.GONE);
                    chatEt.setVisibility(View.VISIBLE);
                    showKeyBoard(chatEt);
                }

                break;
            case R.id.chat_btn_sendtext:
                setTextMessage();
                chatEt.setText("");
                break;
        }
    }

    @OnClick(R.id.chat_bt)
    public void onViewClicked() {

    }
}
