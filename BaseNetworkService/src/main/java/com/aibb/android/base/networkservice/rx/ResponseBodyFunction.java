package com.aibb.android.base.networkservice.rx;

import android.util.Pair;

import com.aibb.android.base.networkservice.http.HttpException;

import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class ResponseBodyFunction<T> implements Function<Response<T>, Pair<Response<T>, T>> {

    @Override
    public Pair<Response<T>, T> apply(Response<T> response) throws Exception {
        if (response.isSuccessful()) {
            handle(response.body());
            return new Pair<>(response, response.body());
        } else {
            HttpException exception = HttpException.create(response);
            exception.handle();
            throw exception;
        }
    }

    public void handle(T t) {

    }
}
