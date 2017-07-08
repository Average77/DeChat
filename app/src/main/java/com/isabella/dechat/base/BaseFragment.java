package com.isabella.dechat.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * description
 * Created by 张芸艳 on 2017/7/4.
 */

public abstract class BaseFragment<V,T extends BasePresenter<V>> extends IFragment {
    public T presenter;
    public abstract T initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
