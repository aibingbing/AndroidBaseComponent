package com.aibb.android.base.example.mvp

import com.aibb.android.base.mvp.BaseMvpPresenter
import com.aibb.android.base.networkservice.DataCallback
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/23 <br>
 * Desc:        <br>
 */
class LoginPresenter : BaseMvpPresenter<LoginView>() {
    fun login(userName: String, password: String) {
        Observable.just(true)
            .delay(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DataCallback<Boolean>() {
                override fun successful(t: Boolean?, response: Response<*>?) {
                    mvpView.loginSuccess(userName, password)
                }

                override fun failed(message: String?, call: Call<Boolean>?) {
                }
            })
    }
}