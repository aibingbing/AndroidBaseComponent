package com.aibb.android.base.networkservice.rx;

import io.reactivex.functions.Function;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
public class VoidFunction<T> implements Function<T, VoidFunction.VoidData> {
    @Override
    public VoidData apply(T t) throws Exception {
        return new VoidData();
    }

    public static class VoidData {

    }
}
