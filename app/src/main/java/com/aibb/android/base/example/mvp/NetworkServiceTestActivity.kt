package com.aibb.android.base.example.mvp

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.R
import com.aibb.android.base.example.network.pojo.GithubRepos
import com.aibb.android.base.example.mvp.presenter.NetworkServiceTestPresenter
import com.aibb.android.base.example.mvp.view.NetworkServiceTestView
import com.aibb.android.base.example.mvp.adapter.RecyclerViewAdapter
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import com.kingja.loadsir.callback.HintCallback
import com.kingja.loadsir.callback.ProgressCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlinx.android.synthetic.main.net_service_test_layout.*


/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
@MvpPresenterInject(values = [NetworkServiceTestPresenter::class])
class NetworkServiceTestActivity : MyBaseMvpActivity(),
    NetworkServiceTestView {

    @MvpPresenterVariable
    lateinit var mNetworkServicePresenter: NetworkServiceTestPresenter
    lateinit var mLoadService: LoadService<Any>
    lateinit var mAdapter: RecyclerViewAdapter
    private val mReposList = ArrayList<GithubRepos>()

    override fun getLayoutId(): Int {
        return R.layout.net_service_test_layout
    }

    override fun initialize() {
        initLoadSir()
        initRecyclerView()
        mNetworkServicePresenter.loadData()
    }

    private fun initLoadSir() {
        val loadingCallback = ProgressCallback.Builder()
            .setTitle("Loading")
            //.setAboveSuccess(true)// attach loadingView above successView
            .build()

        val hintCallback = HintCallback.Builder()
            .setTitle("Error")
            .setSubTitle("Sorry, buddy, I will try it again.")
            .setHintImg(R.drawable.error)
            .build()

        val loadSir = LoadSir.Builder()
            .addCallback(loadingCallback)
            .addCallback(hintCallback)
            .setDefaultCallback(ProgressCallback::class.java)
            .build()

        mLoadService = loadSir.register(this) {
            mLoadService.showCallback(ProgressCallback::class.java)
            mNetworkServicePresenter.loadData()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.recycleChildrenOnDetach = true
        recyclerView.layoutManager = layoutManager
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter =
            RecyclerViewAdapter(
                this,
                mReposList
            )
        recyclerView.adapter = mAdapter
    }

    override fun dismissLoading() {
        mLoadService.showSuccess()
    }

    override fun onLoadDataFailed() {
        mLoadService.showCallback(HintCallback::class.java)
    }

    override fun onLoadDataSuccess(data: List<GithubRepos>) {
        mReposList.clear()
        mReposList.addAll(data)
        mAdapter.notifyDataSetChanged()
    }
}