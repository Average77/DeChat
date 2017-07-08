package com.isabella.dechat.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * description
 * Created by 张芸艳 on 2017/7/4.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends IActivity {
    public T presenter;

    public abstract T initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
