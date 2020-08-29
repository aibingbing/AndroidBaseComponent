package com.aibb.android.base.networkservice.rx;

import java.net.SocketTimeoutException;

import io.reactivex.functions.Predicate;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class RetryFunction implements Predicate<Throwable> {
    public static final int MAX_RETRY_COUNT = 3;
    private int mRetryCount = 0;

    @Override
    public boolean test(Throwable throwable) throws Exception {
        // 请求超时重试
        if (throwable instanceof SocketTimeoutException && mRetryCount < MAX_RETRY_COUNT) {
            mRetryCount++;
            return true;
        }
        return false;
    }
}
