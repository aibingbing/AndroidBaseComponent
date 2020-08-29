package com.aibb.android.base.networkservice.rx;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public abstract class HttpFlatMapFunction<T, K> implements Function<T, ObservableSource<Response<K>>> {
}
