package com.aibb.android.base.example;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.room.Room;

import com.aibb.android.base.example.room.db.MyRoomDatabase;
import com.aibb.android.base.networkservice.RetrofitFactory;
import com.qw.soul.permission.SoulPermission;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

import dagger.hilt.android.HiltAndroidApp;
import ren.yale.android.cachewebviewlib.CacheType;
import ren.yale.android.cachewebviewlib.ResourceInterceptor;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptor;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

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
        initSoulPermission();
        initWebViewCacheIntercept();
        initTencentX5();
    }

    private void initWebViewCacheIntercept() {
        WebViewCacheInterceptor.Builder builder = new WebViewCacheInterceptor.Builder(this);

        builder.setCachePath(new File(this.getCacheDir(), "cache_path_name"))//设置缓存路径，默认getCacheDir，名称CacheWebViewCache
                .setCacheSize(1024 * 1024 * 200)//设置缓存大小，默认100M
                .setConnectTimeoutSecond(20)//设置http请求链接超时，默认20秒
                .setReadTimeoutSecond(20)//设置http请求链接读取超时，默认20秒
                .setDebug(BuildConfig.DEBUG)
                .setCacheType(CacheType.FORCE);//设置缓存为正常模式，默认模式为强制缓存静态资源

//        builder.setResourceInterceptor(new ResourceInterceptor() {
//            @Override
//            public boolean interceptor(String url) {
//                return false;
//            }
//        });
        WebViewCacheInterceptorInst.getInstance().init(builder);
    }

    private void initTencentX5() {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {

            @Override
            public void onCoreInitFinished() {
                Log.e(TAG, "Tencent X5 onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e(TAG, "Tencent X5 onViewInitFinished:" + b);
            }
        });
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

    private void initSoulPermission() {
        SoulPermission.init(this);
        //设置跳过老的权限系统（老的系统默认权限直接授予）
        SoulPermission.skipOldRom(true);
        //设置debug模式(看日志打印)
        SoulPermission.setDebug(BuildConfig.DEBUG);
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
