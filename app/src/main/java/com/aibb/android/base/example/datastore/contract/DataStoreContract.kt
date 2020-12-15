package com.aibb.android.base.example.datastore.contract

import com.aibb.android.base.mvp.IBaseMvpView

interface DataStoreContract {
    interface IDataStorePresenter {
        fun loadData()
    }

    interface IDataStoreView : IBaseMvpView {
        fun onLoadDataSuccess()
        fun onLoadDataFailed(msg: String)
    }
}