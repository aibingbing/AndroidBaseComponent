package com.aibb.android.base.networkservice;

import com.aibb.android.base.networkservice.http.HttpException;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public abstract class DataCallback<T> implements Callback<T>, Observer<T> {
    public DataCallback() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            successful(response.body(), response);
        } else {
            switch (response.code()) {
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    // token错误
                    failed(
                            RetrofitFactory.getApplication().getString(R.string.http_error_message_not_authentication),
                            call
                    );
                    break;
                case HttpURLConnection.HTTP_FORBIDDEN:
                    // 权限错误
                    failed(
                            RetrofitFactory.getApplication().getString(R.string.http_error_message_not_forbidden),
                            call
                    );
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    failed(
                            RetrofitFactory.getApplication().getString(R.string.http_error_message_not_found),
                            call
                    );
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    failed(
                            RetrofitFactory.getApplication().getString(R.string.http_error_message_server_error),
                            call
                    );
                    break;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    failed(new HttpException.BadRequestException(response).getMessage(), call);
                    break;
                default:
                    failed(RetrofitFactory.getApplication()
                            .getString(R.string.http_error_message), call);
                    break;
            }
        }
    }

    public void httpFailed(HttpException e) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        handleError(t);
        t.printStackTrace();
    }

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    @Override
    public void onError(Throwable throwable) {
        handleError(throwable);
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof HttpException) {
            httpFailed((HttpException) throwable);
            try {
                String errorBody = ((HttpException) throwable).response().errorBody().string();
                JSONObject jsonObject = new JSONObject(errorBody);
                failed(jsonObject.getString("detail"), null);
            } catch (Exception e) {
                failed(throwable.getMessage(), null);
            }
        } else if (throwable instanceof SocketTimeoutException) {
            failed(RetrofitFactory.getApplication().getString(R.string.http_error_message_runtime), null);
        } else if (throwable instanceof UnknownHostException) {
            failed(RetrofitFactory.getApplication().getString(R.string.hint_network_error), null);
        } else if (throwable instanceof CompositeException) {
            // rxjava mergeDelayError 会将所有的error封装在CompositeException里面，暂时只取第一个吧
            CompositeException t = (CompositeException) throwable;
            if (t.getExceptions() != null && !t.getExceptions().isEmpty()) {
                handleError(t.getExceptions().get(0));
            }
        } else {
            failed(RetrofitFactory.getApplication().getString(R.string.http_error_message), null);
        }
    }

    @Override
    public void onNext(T t) {
        successful(t, null);
    }

    @Override
    public void onComplete() {

    }

    public abstract void successful(T t, Response response);

    public abstract void failed(String message, Call<T> call);

}
