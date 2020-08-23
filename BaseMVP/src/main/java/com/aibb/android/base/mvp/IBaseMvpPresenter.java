package com.aibb.android.base.mvp;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
public interface IBaseMvpPresenter<T extends IBaseMvpView> {
    void attachView(T view);

    void detachView();

    boolean isAttachView();

    void destory();
}
