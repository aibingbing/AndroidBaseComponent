package com.aibb.android.base.example.network.view

import com.aibb.android.base.example.MyBaseMvpActivity
import com.aibb.android.base.example.R
import com.aibb.android.base.example.network.pojo.GithubRepos
import com.aibb.android.base.mvp.BaseMvpActivity
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import kotlinx.android.synthetic.main.network_service_test_layout.*

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
@MvpPresenterInject(values = [NetworkServiceTestPresenter::class])
class NetworkServiceTestActivity : MyBaseMvpActivity(), NetworkServiceTestView {

    @MvpPresenterVariable
    lateinit var mNetworkServicePresenter: NetworkServiceTestPresenter

    override fun getLayoutId(): Int {
        return R.layout.network_service_test_layout
    }

    override fun initialize() {
        // show loading
        mNetworkServicePresenter.loadData()
    }

    override fun dismissLoading() {
    }

    override fun onLoadDataFailed() {
    }

    override fun onLoadDataSuccess(data: MutableList<GithubRepos>?) {
        val strArray = data?.map { it.name + "\n" }
        var str = ""
        strArray?.forEach { str += it }
        textView.text = str
    }
}