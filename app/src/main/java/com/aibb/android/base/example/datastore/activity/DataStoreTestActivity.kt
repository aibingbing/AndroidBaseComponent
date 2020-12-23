package com.aibb.android.base.example.datastore.activity

import android.util.Log
import com.aibb.android.base.example.R
import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.datastore.contract.DataStoreContract
import com.aibb.android.base.example.datastore.presenter.DataStorePresenter
import com.aibb.android.base.example.datastore.proto.UserInfoPreferences
import com.aibb.android.base.mvp.annotation.MvpPresenterInject
import com.aibb.android.base.mvp.annotation.MvpPresenterVariable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.data_store_activity.*

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
        initView()
        dataStorePresenter.initDataStore()
        dataStorePresenter.loadPreferenceData()
        dataStorePresenter.loadProtoData()
    }

    private fun initView() {
        preferenceAddUserInfoBtn.setOnClickListener {
            dataStorePresenter.setPreferenceUserInfo()
        }
        preferenceDeleteUserInfoBtn.setOnClickListener {
            dataStorePresenter.deletePreferenceUserInfo()
        }
        protoAddUserInfoBtn.setOnClickListener {
            dataStorePresenter.setProtoUserInfo()
        }
        protoDeleteUserInfoBtn.setOnClickListener {
            dataStorePresenter.deleteProtoUserInfo()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.data_store_activity
    }

    override fun onLoadPreferenceDataSuccess(userInfoStr: String) {
        Log.i(TAG, "onLoadPreferenceDataSuccess")
        preferenceUserContentTextView.text = userInfoStr
    }

    override fun onLoadPreferenceDataFailed(msg: String) {
        Log.i(TAG, "onLoadPreferenceDataFailed:[msg=$msg]")
    }

    override fun onLoadProtoDataSuccess(userInfo: String) {
        protoUserContentTextView.text = userInfo
    }

    override fun onLoadProtoDataFailed(msg: String) {
        Log.i(TAG, "onLoadProtoDataFailed:[msg=$msg]")
    }
}