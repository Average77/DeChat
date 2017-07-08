package com.isabella.dechat.base;

/**
 * description
 * Created by 张芸艳 on 2017/7/4.
 */

public abstract class BasePresenter<V> {
    public V view;

    public void attach(V view) {
        this.view = view;
    }

    public void detach() {
        this.view = null;
    }
}
