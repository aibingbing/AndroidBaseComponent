package com.aibb.android.base.example;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.aibb.android.base.log.LogCollect;
import com.aibb.android.base.networkservice.RetrofitFactory;
import com.tencent.mars.xlog.Log;

import java.util.Locale;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
@HiltAndroidApp
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
        initLog();
        initCrashCatch();
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

    private void initCrashCatch() {
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                LogCollect.e("Crash", e.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
