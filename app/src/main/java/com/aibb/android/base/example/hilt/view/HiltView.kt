package com.aibb.android.base.example.hilt.view

import com.aibb.android.base.example.hilt.pojo.HiltItem
import com.aibb.android.base.mvp.IBaseMvpView

interface HiltView : IBaseMvpView {
    fun dismissLoading()
    fun onLoadDataSuccess(data: List<HiltItem>)
    fun onLoadDataFailed()
}