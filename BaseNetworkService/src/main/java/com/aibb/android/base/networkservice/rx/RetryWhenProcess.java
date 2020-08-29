package com.aibb.android.base.networkservice.rx;

import android.util.Pair;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class RetryWhenProcess implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int mRetryCount;
    private Integer[] mRetryIntervalArray;

    public RetryWhenProcess(int retryCount) {
        this.mRetryCount = retryCount;
        this.mRetryIntervalArray = new Integer[]{0, 5, 10, 15, 30, 60};
    }

    public RetryWhenProcess(int retryCount, Integer[] retryIntervalArray) {
        this.mRetryCount = retryCount;
        this.mRetryIntervalArray = retryIntervalArray;
    }

    @Override
    public Observable<?> apply(final Observable<? extends Throwable> errors) {
        return errors.zipWith(
                Observable.range(1, mRetryCount + 1),
                new BiFunction<Throwable, Integer, Pair<Throwable, Integer>>() {
                    @Override
                    public Pair<Throwable, Integer> apply(Throwable throwable, Integer integer) {
                        return new Pair<Throwable, Integer>(throwable, integer);
                    }
                }
        ).flatMap(new Function<Pair<Throwable, Integer>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Pair<Throwable, Integer> pair) {
                int retryCount = pair.second;
                if (retryCount == mRetryCount + 1) {
                    return Observable.error(pair.first);
                }
                Integer[] intervalSecond = mRetryIntervalArray;
                int index = retryCount - 1;
                if (index < 0) {
                    index = 0;
                } else if (index > intervalSecond.length - 1) {
                    index = intervalSecond.length - 1;
                }
                return Observable.timer(intervalSecond[index], TimeUnit.SECONDS);
            }
        });
    }
}
