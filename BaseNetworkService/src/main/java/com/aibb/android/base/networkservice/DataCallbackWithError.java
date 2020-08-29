package com.aibb.android.base.networkservice;

import com.aibb.android.base.networkservice.http.HttpError;
import com.aibb.android.base.networkservice.http.HttpException;
import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public abstract class DataCallbackWithError<T, E extends HttpError> extends DataCallback<T> {
    public DataCallbackWithError() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        List<Integer> interceptHttpCode = new ArrayList<Integer>() {
            {
                add(HttpURLConnection.HTTP_BAD_REQUEST);
                add(HttpURLConnection.HTTP_FORBIDDEN);
                add(HttpURLConnection.HTTP_NOT_FOUND);
            }
        };
        if (!response.isSuccessful() && interceptHttpCode.contains(response.code())) {
            parseErrorBody(response.code(), response.errorBody());
            return;
        }
        super.onResponse(call, response);
    }

    private void parseErrorBody(int httpCode, ResponseBody errorBody) {
        String errorDetail = RetrofitFactory.getApplication().getString(R.string.http_error_message);
        if (errorBody == null) {
            error(httpCode, null);
            return;
        }
        E responseError = null;
        try {
            responseError = RetrofitFactory.getGsonProvider().getGson().fromJson(errorBody.string(), new TypeToken<E>() {
            }.getType());
        } catch (Exception e) {
        } finally {
            error(httpCode, responseError);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof HttpException.BadRequestException) {
            HttpException.BadRequestException exception = (HttpException.BadRequestException) throwable;
            parseErrorBody(exception.getHttpCode(), exception.getErrorBody());
            return;
        } else if (throwable instanceof HttpException.NotFoundException) {
            HttpException.NotFoundException exception = (HttpException.NotFoundException) throwable;
            parseErrorBody(exception.getHttpCode(), exception.getErrorBody());
            return;
        } else if (throwable instanceof HttpException.PermissionForbiddenException) {
            HttpException.PermissionForbiddenException exception = (HttpException.PermissionForbiddenException) throwable;
            parseErrorBody(exception.getHttpCode(), exception.getErrorBody());
            return;
        } else if (throwable instanceof HttpException) {
            failed(throwable.getMessage(), null);
            httpFailed((HttpException) throwable);
        } else if (throwable instanceof SocketTimeoutException) {
            failed(RetrofitFactory.getApplication().getString(R.string.http_error_message_runtime), null);
        } else if (throwable instanceof UnknownHostException) {
            failed(RetrofitFactory.getApplication().getString(R.string.hint_network_error), null);
        } else {
            failed(RetrofitFactory.getApplication().getString(R.string.http_error_message), null);
        }
        super.onError(throwable);
    }


    public abstract void error(int code, E error);
}
