package com.aibb.android.base.example.mvp

import com.aibb.android.base.mvp.BaseMvpPresenter

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
class LoginPresenter : BaseMvpPresenter<LoginView>() {
    fun login() {
        mvpView.loginSuccess()
    }
}