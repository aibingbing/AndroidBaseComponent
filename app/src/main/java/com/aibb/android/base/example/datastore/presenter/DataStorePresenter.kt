package com.aibb.android.base.example.datastore.presenter

import com.aibb.android.base.example.datastore.contract.DataStoreContract
import com.aibb.android.base.mvp.BaseMvpPresenter
import javax.inject.Inject

class DataStorePresenter @Inject constructor() : DataStoreContract.IDataStorePresenter,
    BaseMvpPresenter<DataStoreContract.IDataStoreView>() {

    override fun loadData() {
        mvpView.onLoadDataSuccess()
    }
}