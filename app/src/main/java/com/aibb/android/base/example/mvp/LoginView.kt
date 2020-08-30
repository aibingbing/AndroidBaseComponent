package com.aibb.android.base.example.mvp

import com.aibb.android.base.mvp.IBaseMvpView

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
interface LoginView : IBaseMvpView {
    fun loginSuccess(userName: String, password: String)
}