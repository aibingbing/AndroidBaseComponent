package com.aibb.android.base.mvp;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
public abstract class BaseMvpPresenter<T extends IBaseMvpView> implements IBaseMvpPresenter<T> {
    T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isAttachView() {
        return mView != null;
    }

    @Override
    public void destory() {
    }

    public T getMvpView() {
        return mView;
    }
}
