package com.aibb.android.base.example.mvp

import android.util.Log
import android.widget.Toast
import com.aibb.android.base.example.R
import com.aibb.android.base.mvp.BaseMvpActivity
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import kotlinx.android.synthetic.main.mvp_main_layout.*

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
@MvpPresenterInject(values = [LoginPresenter::class, AccountPresenter::class])
class MvpMainActivity : BaseMvpActivity(), LoginView, AccountView {

    @MvpPresenterVariable
    lateinit var mLoginPresenter: LoginPresenter
    @MvpPresenterVariable
    lateinit var mAccountPresenter: AccountPresenter

    override fun getLayoutId(): Int {
        return R.layout.mvp_main_layout
    }

    override fun initialize() {
        Log.i(javaClass.name, " initialize...")
        btnLogin.setOnClickListener {
            mLoginPresenter.login()
        }
    }

    override fun loginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show()
        mAccountPresenter.saveAccount("abb", "123")
    }

    override fun saveAccountSuccess(userName: String, password: String) {
        Toast.makeText(this, "保存账户成功:${userName} / ${password}", Toast.LENGTH_LONG).show()
    }
}