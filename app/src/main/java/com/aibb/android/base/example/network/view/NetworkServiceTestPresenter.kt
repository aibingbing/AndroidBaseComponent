package com.aibb.android.base.example.network.view

import com.aibb.android.base.example.MyBaseMvpActivity
import com.aibb.android.base.example.network.api.GithubApiService
import com.aibb.android.base.example.network.pojo.GithubRepos
import com.aibb.android.base.mvp.BaseMvpPresenter
import com.aibb.android.base.networkservice.DataCallback
import retrofit2.Call
import retrofit2.Response

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
class NetworkServiceTestPresenter : BaseMvpPresenter<NetworkServiceTestView>() {
    fun loadData() {
        GithubApiService
            .getGithubRespo()
            .compose((mvpView.baseActivity as MyBaseMvpActivity).bindToLifecycle())
            .subscribe(object : DataCallback<List<GithubRepos>>() {
                override fun successful(t: List<GithubRepos>?, response: Response<*>?) {
                    mvpView.dismissLoading()
                    mvpView.onLoadDataSuccess(t)
                }

                override fun failed(message: String?, call: Call<List<GithubRepos>>?) {
                    mvpView.dismissLoading()
                    mvpView.onLoadDataFailed()
                }

            })
    }
}