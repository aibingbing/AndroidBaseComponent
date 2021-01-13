package com.aibb.android.base.example.mvp.activity

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.aibb.android.base.example.R
import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.mvp.presenter.AccountPresenter
import com.aibb.android.base.example.mvp.presenter.LoginPresenter
import com.aibb.android.base.example.mvp.view.AccountView
import com.aibb.android.base.example.mvp.view.LoginView
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.mvp_main_layout.*

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
@MvpPresenterInject(values = [LoginPresenter::class, AccountPresenter::class])
class MvpMainActivity : MyBaseMvpActivity(),
    LoginView,
    AccountView {

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
            val userName = userNameEdittext.text.toString()
            val passwd = passwordEdittext.text.toString()
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passwd)) {
                ToastUtils.showShort(getString(R.string.hint_login_not_empty))
                return@setOnClickListener
            }
            showLoadingDialog(R.string.hint_logining)
            mLoginPresenter.login(userName, passwd)
        }
    }

    override fun loginSuccess(userName: String, password: String) {
        dismissDialog()
        Toast.makeText(this, getString(R.string.hint_login_success), Toast.LENGTH_LONG).show()
        showLoadingDialog(R.string.hint_saving_account)
        mAccountPresenter.saveAccount(userName, password)
    }

    override fun saveAccountSuccess(userName: String, password: String) {
        dismissDialog()
        Toast.makeText(this,
            String.format(getString(R.string.hint_save_account_success), userName, password),
            Toast.LENGTH_LONG
        ).show()
    }
}