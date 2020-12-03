package com.aibb.android.base.example.hilt.presenter

import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.hilt.activity.HiltTestActivity
import com.aibb.android.base.example.hilt.api.RepositoryService
import com.aibb.android.base.example.hilt.pojo.Repository
import com.aibb.android.base.example.hilt.view.IHiltView
import com.aibb.android.base.mvp.BaseMvpPresenter
import com.aibb.android.base.networkservice.DataCallback
import retrofit2.Call
import retrofit2.Response

class HiltPresenter : BaseMvpPresenter<IHiltView>() {
    fun loadData() {
        RepositoryService
            .getSquareGithubRepository()
            .compose((mvpView.baseActivity as HiltTestActivity).bindToLifecycle())
            .subscribe(object : DataCallback<List<Repository>>() {
                override fun successful(t: List<Repository>?, response: Response<*>?) {
                    mvpView.dismissLoading()
                    t?.also {
                        mvpView.onLoadDataSuccess(t)
                    }
                }

                override fun failed(message: String?, call: Call<List<Repository>>?) {
                    mvpView.dismissLoading()
                    mvpView.onLoadDataFailed()
                }
            })
    }
}