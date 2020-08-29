package com.aibb.android.base.networkservice;

import android.util.Pair;

import com.aibb.android.base.networkservice.rx.ResponseBodyFunction;
import com.aibb.android.base.networkservice.rx.ResponseFunction;
import com.aibb.android.base.networkservice.rx.RetryFunction;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class RxApi {
    public static <T> Observable<T> createApi(Observable<Response<T>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .map(new ResponseFunction<T>())
                .retry(5, new RetryFunction())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable<Pair<Response<T>, T>> createResponseApi(Observable<Response<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .map(new ResponseBodyFunction<T>())
                .retry(5, new RetryFunction())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ResponseBody> createNoContentResponseApi(Observable<Response<ResponseBody>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .retry(5, new RetryFunction())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ResponseFunction.NoContentResponseFunction());
    }
}
