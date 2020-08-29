package com.aibb.android.base.mvp;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
public abstract class BaseMvpActivity extends AppCompatActivity implements IBaseMvpView {
    private PresenterProvider mPresenterProvider;
    private PresenterDispatch mPresenterDispatch;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mUnBinder = ButterKnife.bind(this);
        mPresenterProvider = PresenterProvider.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProvider);
        mPresenterDispatch.attachView(this);
        initialize();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        mPresenterDispatch.detachView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public BaseMvpActivity getBaseActivity() {
        return this;
    }

    protected PresenterProvider getPresenterProviders() {
        return mPresenterProvider;
    }

    protected abstract void initialize();

    protected abstract int getLayoutId();
}
