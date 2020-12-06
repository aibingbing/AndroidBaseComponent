package com.aibb.android.base.example.hilt.presenter

import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.hilt.api.HiltService
import com.aibb.android.base.example.hilt.pojo.HiltItem
import com.aibb.android.base.example.hilt.view.HiltView
import com.aibb.android.base.mvp.BaseMvpPresenter
import com.aibb.android.base.networkservice.DataCallback
import retrofit2.Call
import retrofit2.Response

class HiltPresenter : BaseMvpPresenter<HiltView>() {
    fun loadData() {
        HiltService
            .getGithubRespo()
            .compose((mvpView.baseActivity as MyBaseMvpActivity).bindToLifecycle())
            .subscribe(object : DataCallback<List<HiltItem>>() {
                override fun successful(t: List<HiltItem>?, response: Response<*>?) {
                    mvpView.dismissLoading()
                    t?.also {
                        mvpView.onLoadDataSuccess(t)
                    }
                }

                override fun failed(message: String?, call: Call<List<HiltItem>>?) {
                    mvpView.dismissLoading()
                    mvpView.onLoadDataFailed()
                }
            })
    }
}