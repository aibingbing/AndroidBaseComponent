package com.aibb.android.base.example.datastore.contract

import com.aibb.android.base.example.datastore.proto.UserInfoPreferences
import com.aibb.android.base.mvp.IBaseMvpView

interface DataStoreContract {
    interface IDataStorePresenter {
        fun loadPreferenceData()
        fun setPreferenceUserInfo()
        fun deletePreferenceUserInfo()
        fun loadProtoData()
        fun setProtoUserInfo()
        fun deleteProtoUserInfo()
    }

    interface IDataStoreView : IBaseMvpView {
        fun onLoadPreferenceDataSuccess(userInfo: String)
        fun onLoadPreferenceDataFailed(msg: String)
        fun onLoadProtoDataSuccess(userInfo: String)
        fun onLoadProtoDataFailed(msg: String)
    }
}