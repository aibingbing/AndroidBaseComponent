package com.aibb.android.base.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
public abstract class BaseMvpFragment extends Fragment implements IBaseMvpView {
    private PresenterProvider mPresenterProvider;
    private PresenterDispatch mPresenterDispatch;
    private View mRootView;
    private Unbinder mUnBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mUnBinder = ButterKnife.bind(this, mRootView);
            onCreateView(container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenterProvider = PresenterProvider.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProvider);
        mPresenterDispatch.attachView(this);
    }

    @Override
    public void onDestroyView() {
        mPresenterDispatch.detachView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public BaseMvpActivity getBaseActivity() {
        return (BaseMvpActivity) getActivity();
    }

    public PresenterProvider getPresenterProviders() {
        return mPresenterProvider;
    }

    public View findViewById(@IdRes int id) {
        View view;
        if (mRootView != null) {
            view = mRootView.findViewById(id);
            return view;
        }
        return null;
    }

    protected abstract void onCreateView(View container, Bundle bundle);

    protected abstract void initialize();

    protected abstract int getLayoutId();
}
