package com.aibb.android.base.mvp.annotation;

import com.aibb.android.base.mvp.BaseMvpPresenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MvpPresenterInject {
    Class<? extends BaseMvpPresenter>[] values() default {};
}
