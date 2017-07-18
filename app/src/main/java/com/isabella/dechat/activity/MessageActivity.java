package com.isabella.dechat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.isabella.dechat.R;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, "79");
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.fram, chatFragment).commit();
      //  EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }
}
