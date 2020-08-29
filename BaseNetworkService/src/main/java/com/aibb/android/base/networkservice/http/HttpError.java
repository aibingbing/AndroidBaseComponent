package com.aibb.android.base.networkservice.http;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class HttpError {
    public int code;
    public String detail;

    public HttpError() {
    }

    public HttpError(String detail) {
        this.detail = detail;
    }
}
