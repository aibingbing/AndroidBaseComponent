package com.aibb.android.base.example;

import android.app.Application;

import com.aibb.android.base.log.LogCollect;
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
        initRetrofit();
        initLog();
    }

    private void initRetrofit() {
        RetrofitFactory.initApplicatonContext(this);
        RetrofitFactory.addGlobalHeader("Accept-Language", Locale.getDefault().getLanguage());
    }

    private void initLog() {
        LogCollect.init(
                getApplicationContext(),
                LogCollect.newConfig()
                        .logLevel(BuildConfig.DEBUG ? LogCollect.DEBUG : LogCollect.INFO)
                        .enableFileLog(true)
                        .enableConsoleLog(BuildConfig.DEBUG)
                        .fileLogMaxAliveTime(5 * 24 * 60 * 60)
        );
    }

}
