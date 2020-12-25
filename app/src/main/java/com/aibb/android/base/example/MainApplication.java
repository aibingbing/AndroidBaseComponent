package com.aibb.android.base.example;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.aibb.android.base.example.room.db.MyRoomDatabase;
import com.aibb.android.base.log.LogCollect;
import com.aibb.android.base.networkservice.RetrofitFactory;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

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

    private static MyRoomDatabase database;
    private static MainApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initRoom();
        initRetrofit();
        initLog();
        initCrashCatch();
        initStetho();
        initLeakCanary();
    }

    private void initRoom() {
        database = Room.databaseBuilder(getApplicationContext(), MyRoomDatabase.class, "db_room")
//                .createFromAsset("database/myapp.db")
//                .fallbackToDestructiveMigration()
                .build();
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
                Log.e("Crash", e.getLocalizedMessage());
            }
        });
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG && !LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MainApplication getInstance() {
        return mContext;
    }

    public MyRoomDatabase getDatabase() {
        return database;
    }
}
