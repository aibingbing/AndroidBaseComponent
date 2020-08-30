package com.aibb.android.base.mvp;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
public class PresenterDispatch {
    private PresenterProvider mProviders;

    public PresenterDispatch(PresenterProvider providers) {
        mProviders = providers;
    }

    public <P extends BaseMvpPresenter> void attachView(IBaseMvpView view) {
        PresenterProvider.PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.attachView(view);
            }
        }
    }

    public <P extends BaseMvpPresenter> void detachView() {
        PresenterProvider.PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.detachView();
            }
        }
    }

    public <P extends BaseMvpPresenter> void onDestroyPresenter() {
        PresenterProvider.PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.destroy();
            }
        }
    }
}
