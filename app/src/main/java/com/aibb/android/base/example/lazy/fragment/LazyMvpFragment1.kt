package com.aibb.android.base.example.lazy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import butterknife.ButterKnife
import com.aibb.android.base.example.R
import com.aibb.android.base.example.base.MyBaseLazyMvpFragment
import com.aibb.android.base.example.mvp.adapter.RecyclerViewAdapter
import com.aibb.android.base.example.network.presenter.NetworkServiceTestPresenter
import com.aibb.android.base.example.network.view.NetworkServiceTestView
import com.aibb.android.base.example.network.pojo.GithubRepos
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import com.blankj.utilcode.utils.ToastUtils
import com.kingja.loadsir.callback.HintCallback
import com.kingja.loadsir.callback.ProgressCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlinx.android.synthetic.main.net_service_test_layout.*


@MvpPresenterInject(values = [NetworkServiceTestPresenter::class])
open class LazyMvpFragment1 : MyBaseLazyMvpFragment(),
    NetworkServiceTestView {

    @MvpPresenterVariable
    open lateinit var mNetworkServicePresenter: NetworkServiceTestPresenter
    lateinit var mLoadService: LoadService<Any>
    lateinit var mAdapter: RecyclerViewAdapter
    private val mReposList = ArrayList<GithubRepos>()

    override fun getLayoutId(): Int {
        return R.layout.net_service_test_layout
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loadingCallback = ProgressCallback.Builder()
            .setTitle("Loading")
            //.setAboveSuccess(true)// attach loadingView above successView
            .build()

        val hintCallback = HintCallback.Builder()
            .setTitle("Error")
            .setSubTitle("Sorry, buddy, I will try it again.")
            .setHintImg(R.drawable.error)
            .build()

        val rootView: View = inflater.inflate(layoutId, container, false)
        ButterKnife.bind(this, rootView)
        mLoadService = LoadSir.Builder()
            .addCallback(loadingCallback)
            .addCallback(hintCallback)
            .build()
            .register(rootView) { v ->
                mLoadService.showCallback(ProgressCallback::class.java)
                mNetworkServicePresenter.loadData()
            }
        onCreateView(container, savedInstanceState)
        return mLoadService.loadLayout
    }

    override fun onCreateView(container: View?, bundle: Bundle?) {
    }

    override fun initialize() {
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        layoutManager.recycleChildrenOnDetach = true
        recyclerView.layoutManager = layoutManager
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mAdapter = RecyclerViewAdapter(requireContext(), mReposList)
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
        ToastUtils.showShortToast(context, "Load JakeWharton's repos success")
    }

    override fun lazyInit() {
        initRecyclerView()
        mLoadService.showCallback(ProgressCallback::class.java)
        mNetworkServicePresenter.loadData()
    }

}