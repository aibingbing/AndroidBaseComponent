package com.aibb.android.base.networkservice.http;

import com.aibb.android.base.networkservice.R;
import com.aibb.android.base.networkservice.RetrofitFactory;

import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public abstract class HttpException extends retrofit2.HttpException {
    public HttpException(Response<?> response) {
        super(response);
    }

    public static HttpException create(Response<?> response) {
        switch (response.code()) {
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                return new UnAuthorizedException(response);
            case HttpURLConnection.HTTP_FORBIDDEN:
                return new PermissionForbiddenException(response);
            case HttpURLConnection.HTTP_NOT_FOUND:
                return new NotFoundException(response);
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                return new ServerErrorException(response);
            case HttpURLConnection.HTTP_BAD_GATEWAY:
                return new BadGateWayException(response);
            case HttpURLConnection.HTTP_BAD_REQUEST:
                return new BadRequestException(response);
            default:
                return new HttpException(response) {
                    @Override
                    public String getMessage() {
                        return RetrofitFactory.getApplication().getString(R.string.http_error_message);
                    }
                };
        }
    }

    public abstract String getMessage();

    public ResponseBody getErrorBody() {
        return response().errorBody();
    }

    public int getHttpCode() {
        return response().code();
    }

    public void handle() {
    }

    public static class UnAuthorizedException extends HttpException {
        public UnAuthorizedException(Response<?> response) {
            super(response);
        }

        @Override
        public String getMessage() {
            return RetrofitFactory.getApplication().getString(R.string.http_error_message_not_authentication);
        }
    }

    public static class PermissionForbiddenException extends HttpException {

        public PermissionForbiddenException(Response<?> response) {
            super(response);
        }

        @Override
        public String getMessage() {
            return RetrofitFactory.getApplication().getString(R.string.http_error_message_not_forbidden);
        }
    }

    public static class NotFoundException extends HttpException {

        public NotFoundException(Response<?> response) {
            super(response);
        }

        @Override
        public String getMessage() {
            return RetrofitFactory.getApplication().getString(R.string.http_error_message_not_found);
        }
    }

    public static class ServerErrorException extends HttpException {

        public ServerErrorException(Response<?> response) {
            super(response);
        }

        @Override
        public String getMessage() {
            return RetrofitFactory.getApplication().getString(R.string.http_error_message_server_error);
        }
    }

    public static class BadGateWayException extends HttpException {

        public BadGateWayException(Response<?> response) {
            super(response);
        }

        @Override
        public String getMessage() {
            return RetrofitFactory.getApplication().getString(R.string.http_error_message);
        }
    }

    public static class BadRequestException extends HttpException {

        public BadRequestException(Response<?> response) {
            super(response);
        }

        @Override
        public String getMessage() {
            return RetrofitFactory.getApplication().getString(R.string.http_error_message);
        }
    }
}
