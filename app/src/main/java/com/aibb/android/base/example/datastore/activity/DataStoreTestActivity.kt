package com.aibb.android.base.example.datastore.activity

import android.util.Log
import com.aibb.android.base.example.R
import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.datastore.contract.DataStoreContract
import com.aibb.android.base.example.datastore.presenter.DataStorePresenter
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@MvpPresenterInject(values = [DataStorePresenter::class])
class DataStoreTestActivity : MyBaseMvpActivity(), DataStoreContract.IDataStoreView {

    companion object {
        private val TAG = this.javaClass.canonicalName
    }

    @MvpPresenterVariable
    lateinit var dataStorePresenter: DataStorePresenter

    override fun initialize() {
        Log.i(TAG, "initialize")
        Log.i(TAG, dataStorePresenter.toString())
        dataStorePresenter.loadData()
    }

    override fun getLayoutId(): Int {
        return R.layout.data_store_activity
    }

    override fun onLoadDataSuccess() {
        Log.i(TAG, "onLoadDataSuccess")
    }

    override fun onLoadDataFailed(msg: String) {
    }
}