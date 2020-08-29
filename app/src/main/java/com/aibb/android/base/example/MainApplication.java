package com.aibb.android.base.example;

import android.app.Application;

import com.aibb.android.base.networkservice.RetrofitFactory;

import java.util.Locale;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitFactory.initApplicatonContext(this);
        RetrofitFactory.addGlobalHeader("Accept-Language", Locale.getDefault().getLanguage());
    }
}
