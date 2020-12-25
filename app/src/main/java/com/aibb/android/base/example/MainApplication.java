package com.aibb.android.base.example;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.aibb.android.base.example.room.db.MyRoomDatabase;
import com.aibb.android.base.networkservice.RetrofitFactory;
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

    private static final String TAG = "MainApplication";
    private static MyRoomDatabase database;
    private static MainApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // retrofit, leakcanary 依赖Application，放onCreate初始化
        initRetrofit();
        initLeakCanary();
    }

    private void initRetrofit() {
        Log.i(TAG, "Retrofit init");
        RetrofitFactory.initApplicatonContext(this);
        RetrofitFactory.addGlobalHeader("Accept-Language", Locale.getDefault().getLanguage());
    }


    private void initLeakCanary() {
        Log.i(TAG, "LeakCanary init");
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

    public static MyRoomDatabase initDatabase(Context context) {
        if (database == null) {
            synchronized (MyRoomDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(
                            context,
                            MyRoomDatabase.class, "db_room")
                            //                .createFromAsset("database/myapp.db")
                            //                .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return database;
    }

    public MyRoomDatabase getDatabase() {
        return database;
    }
}
