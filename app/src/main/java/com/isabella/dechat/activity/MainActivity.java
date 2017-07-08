package com.isabella.dechat.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.isabella.dechat.R;
import com.isabella.dechat.fragment.FreshFragment;
import com.isabella.dechat.fragment.FriendFragment;
import com.isabella.dechat.fragment.MessageFragment;
import com.isabella.dechat.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.container_fram_main)
    FrameLayout containerFramMain;
    @BindView(R.id.tabber_rg_main)
    RadioGroup tabberRgMain;
    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        createFragment(savedInstanceState);
        switchFragment(0);

        tabberRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {

                    case R.id.tabber_rb_message:

                        switchFragment(0);
                        break;

                    case R.id.tabber_rb_friend:
                        switchFragment(1);

                        break;

                    case R.id.tabber_rb_fresh:
                        switchFragment(2);

                        break;

                    case R.id.tabber_rb_me:
                        switchFragment(3);

                        break;

                }
            }
        });
    }


    public void createFragment(Bundle savedInstanceState) {

        MessageFragment messageFragment = (MessageFragment) fragmentManager.findFragmentByTag("MessageFragment");
        FriendFragment friendFragment = (FriendFragment) fragmentManager.findFragmentByTag("FriendFragment");
        FreshFragment freshFragment = (FreshFragment) fragmentManager.findFragmentByTag("FreshFragment");
        MyFragment myFragment = (MyFragment) fragmentManager.findFragmentByTag("MyFragment");
        if (messageFragment == null) {
            messageFragment = new MessageFragment();
        }

        if (friendFragment == null) {
            friendFragment = new FriendFragment();
        }

        if (freshFragment == null) {
            freshFragment = new FreshFragment();
        }
        if (myFragment == null) {
            myFragment = new MyFragment();
        }

        fragments.add(messageFragment);
        fragments.add(friendFragment);
        fragments.add(freshFragment);
        fragments.add(myFragment);


    }


    public void switchFragment(int pos) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (!fragments.get(pos).isAdded()) {

            transaction.add(R.id.container_fram_main, fragments.get(pos), fragments.get(pos).getClass().getSimpleName());
        }

        for (int i = 0; i < fragments.size(); i++) {

            if (i == pos) {
                transaction.show(fragments.get(pos));
            } else {
                transaction.hide(fragments.get(i));
            }

        }
        transaction.commit();


    }


}
