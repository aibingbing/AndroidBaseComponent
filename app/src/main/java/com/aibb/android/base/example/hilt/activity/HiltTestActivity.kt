package com.aibb.android.base.example.hilt.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.aibb.android.base.example.R
import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.hilt.adapter.HiltAdapter
import com.aibb.android.base.example.hilt.pojo.HiltItem
import com.aibb.android.base.example.hilt.presenter.HiltPresenter
import com.aibb.android.base.example.hilt.view.HiltView
import com.aibb.android.base.example.widget.GridDividerItemDecoration
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import com.blankj.utilcode.util.ToastUtils
import com.kingja.loadsir.callback.HintCallback
import com.kingja.loadsir.callback.ProgressCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.hilt_test_layout.*
import javax.inject.Inject

@AndroidEntryPoint
@MvpPresenterInject(values = [HiltPresenter::class])
class HiltTestActivity : MyBaseMvpActivity(), HiltView {
    @MvpPresenterVariable
    lateinit var repositoryPresenter: HiltPresenter
    lateinit var loadService: LoadService<Any>

    @Inject
    lateinit var adapter: HiltAdapter
    private val reposList = ArrayList<HiltItem>()
    private var gridSpacingItemDecoration: GridDividerItemDecoration? = null

    override fun initialize() {
        initLoadSir()
        initRecyclerView()
        repositoryPresenter.loadData()
    }

    override fun getLayoutId(): Int {
        return R.layout.hilt_test_layout
    }

    override fun dismissLoading() {
        loadService.showSuccess()
    }

    override fun onLoadDataSuccess(data: List<HiltItem>) {
        reposList.clear()
        reposList.addAll(data)
        adapter.setNewData(reposList)
        ToastUtils.showShort("Load Square's repos success")
    }

    override fun onLoadDataFailed() {
        loadService.showCallback(HintCallback::class.java)
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

        loadService = loadSir.register(this) {
            loadService.showCallback(ProgressCallback::class.java)
            repositoryPresenter.loadData()
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 5)
        recyclerView.layoutManager = gridLayoutManager
        gridSpacingItemDecoration?.also {
            recyclerView.removeItemDecoration(it)
        }
        gridSpacingItemDecoration =
            GridDividerItemDecoration(
                5,
                resources.getDimensionPixelSize(R.dimen.main_module_item_divide),
                resources.getDimensionPixelSize(R.dimen.main_module_item_divide)
            )
        recyclerView.addItemDecoration(gridSpacingItemDecoration!!)
        recyclerView.adapter = adapter
    }
}