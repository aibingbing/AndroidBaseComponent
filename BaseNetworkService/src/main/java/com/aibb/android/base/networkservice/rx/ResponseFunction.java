package com.aibb.android.base.networkservice.rx;

import com.aibb.android.base.networkservice.http.HttpException;

import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Response;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class ResponseFunction<T> implements Function<Response<T>, T> {
    @Override
    public T apply(Response<T> response) throws Exception {
        if (response != null && response.isSuccessful()) {
            handle(response.body());
            return response.body();
        } else {
            assert response != null;
            HttpException exception = HttpException.create(response);
            exception.handle();
            throw exception;
        }
    }

    public void handle(T t) {
    }

    public static class NoContentResponseFunction extends ResponseFunction<ResponseBody> {
        @Override
        public ResponseBody apply(Response<ResponseBody> response) throws Exception {
            super.apply(response);
            return new ResponseBody() {
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public long contentLength() {
                    return 0;
                }

                @Override
                public BufferedSource source() {
                    return null;
                }
            };
        }
    }
}
