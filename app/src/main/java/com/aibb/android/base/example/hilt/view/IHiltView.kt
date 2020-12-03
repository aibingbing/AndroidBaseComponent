package com.aibb.android.base.example.hilt.view

import com.aibb.android.base.example.hilt.pojo.Repository
import com.aibb.android.base.mvp.IBaseMvpView

interface IHiltView : IBaseMvpView {
    fun dismissLoading()
    fun onLoadDataSuccess(data: List<Repository>)
    fun onLoadDataFailed()
}
