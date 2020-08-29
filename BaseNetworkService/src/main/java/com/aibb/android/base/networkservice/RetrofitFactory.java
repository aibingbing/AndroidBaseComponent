package com.aibb.android.base.networkservice;

import android.app.Application;
import android.content.Context;

import com.aibb.android.base.networkservice.gson.GsonProvider;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public final class RetrofitFactory {

    /**
     * 提供Application引用，给HTTP错误提示用
     */
    private static Application mApplication;

    /**
     * gson解析的单例类
     */
    private static GsonProvider mGsonProvider;

    /**
     * 默认请求超时时间 10秒
     */
    private static final int DEFAULT_TIME_OUT_SECONDS = 10;

    /**
     * 使用static变量存储全局所有请求都需要的Header，每次请求都带上
     */
    private static HashMap<String, String> mGlobalHeaderMap = new HashMap<>();

    /***
     * 初始化Application，提供给getString()用
     * @param application
     */
    public static void initApplicatonContext(Application application) {
        mApplication = application;
    }

    /**
     * 获取ApplicationContext
     *
     * @return
     */
    public static Application getApplication() {
        return mApplication;
    }

    /**
     * 获取GsonProvider
     *
     * @return
     */
    public static GsonProvider getGsonProvider() {
        return mGsonProvider;
    }

    /**
     * 添加全局使用的请求Header
     *
     * @param headerMap Header Map
     */
    public static void addAllGlobalHeader(Map<String, String> headerMap) {
        mGlobalHeaderMap.putAll(headerMap);
    }

    /**
     * 移除全局使用的请求Header
     *
     * @param key Header key
     */
    public static void removeGlobalHeader(String key) {
        mGlobalHeaderMap.remove(key);
    }

    /**
     * 添加全局使用的请求Header
     *
     * @param key   Header key
     * @param value Header value
     */
    public static void addGlobalHeader(String key, String value) {
        mGlobalHeaderMap.put(key, value);
    }

    /**
     * 获取全局使用的Header Map
     *
     * @return
     */
    public static HashMap<String, String> getGlobalHeaderMap() {
        return mGlobalHeaderMap;
    }

    private static Retrofit getRetrofit(String baseUrl, int timeout, GsonProvider gsonProvider) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new OKHttpRequestInterceptor());
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        httpClient.connectTimeout(timeout, TimeUnit.SECONDS);
        httpClient.readTimeout(timeout, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new OKHttpLogger());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonProvider.getGson()))
                .build();
    }

    public static <T> T create(String baseUrl, Class<T> clazz) {
        mGsonProvider = GsonProvider.getInstance();
        return getRetrofit(baseUrl, DEFAULT_TIME_OUT_SECONDS, mGsonProvider).create(clazz);
    }

    public static <T> T create(String baseUrl, int timeout, Class<T> clazz) {
        mGsonProvider = GsonProvider.getInstance();
        return getRetrofit(baseUrl, timeout, mGsonProvider).create(clazz);
    }

    public static <T> T create(String baseUrl, int timeout, GsonProvider gsonProvider, Class<T> clazz) {
        mGsonProvider = gsonProvider;
        return getRetrofit(baseUrl, timeout, mGsonProvider).create(clazz);
    }

    private static class OKHttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            if (message != null && BuildConfig.DEBUG) {
                if (message.startsWith("{") || message.startsWith("[")) {
                    Logger.i(message);
                } else {
                    Logger.d(message);
                }
            }
        }
    }

    private static class OKHttpRequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder build = original.newBuilder();
            for (Map.Entry<String, String> entry : mGlobalHeaderMap.entrySet()) {
                Logger.i(entry.getValue());
                if (entry.getKey() != null && entry.getValue() != null) {
                    build.header(entry.getKey(), entry.getValue());
                }
            }
            build.method(original.method(), original.body());
            return chain.proceed(build.build());
        }
    }
}
