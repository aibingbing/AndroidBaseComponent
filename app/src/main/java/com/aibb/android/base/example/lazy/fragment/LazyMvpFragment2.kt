package com.aibb.android.base.example.lazy.fragment

import com.aibb.android.base.example.network.presenter.NetworkServiceTestPresenter
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable

@MvpPresenterInject(values = [NetworkServiceTestPresenter::class])
class LazyMvpFragment2 : LazyMvpFragment1(){
    @MvpPresenterVariable
    override lateinit var mNetworkServicePresenter: NetworkServiceTestPresenter
}