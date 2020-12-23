package com.aibb.android.base.example.datastore.presenter

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.createDataStore
import com.aibb.android.base.example.datastore.contract.DataStoreContract
import com.aibb.android.base.example.datastore.proto.UserInfoPreferences
import com.aibb.android.base.example.datastore.serializer.UserInfoSerializer
import com.aibb.android.base.mvp.BaseMvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStorePresenter @Inject constructor() : DataStoreContract.IDataStorePresenter,
    BaseMvpPresenter<DataStoreContract.IDataStoreView>() {

    companion object {
        const val TAG = "DataStorePresenter"
    }

    lateinit var protoDataStore: DataStore<UserInfoPreferences>
    lateinit var preferencesDataStore: DataStore<Preferences>
    var userIdKey = preferencesKey<Int>("id")
    var userNameKey = preferencesKey<String>("name")
    var userAgeKey = preferencesKey<Int>("age")
    var userTelephoneKey = preferencesKey<String>("telephone")
    var userCityKey = preferencesKey<String>("city")

    fun initDataStore() {
        preferencesDataStore = mvpView.baseActivity.createDataStore(name = "user_info")
        protoDataStore = mvpView.baseActivity.createDataStore(
            "user_info_proto",
            serializer = UserInfoSerializer
        )
    }

    override fun loadPreferenceData() {
        Log.i(TAG, "loadPreferenceData")
        GlobalScope.launch {
            preferencesDataStore.data
                .catch {
                    mvpView.onLoadPreferenceDataFailed("load data error")
                }
                .collect {
                    val userInfoString =
                        "userId:${it[userIdKey]} userName:${it[userNameKey]} userAge:${it[userAgeKey]} userTel:${it[userTelephoneKey]} userCity:${it[userCityKey]}"
                    withContext(Dispatchers.Main) {
                        mvpView.onLoadPreferenceDataSuccess(userInfoString)
                    }
                }
        }
    }

    override fun setPreferenceUserInfo() {
        Log.i(TAG, "setPreferenceUserInfo")
        GlobalScope.launch(Dispatchers.IO) {
            val randomInt = java.util.Random().nextInt(100)
            val randomAge = java.util.Random().nextInt(50)
            preferencesDataStore.edit { value ->
                value[userIdKey] = randomInt
                value[userNameKey] = "User$randomInt"
                value[userAgeKey] = randomAge
                value[userTelephoneKey] = "136****00$randomInt"
                value[userCityKey] = "beijing$randomInt"

                Log.i(
                    TAG,
                    "userId:${value[userIdKey]} userName:${value[userNameKey]} userAge:${value[userAgeKey]} userTel:${value[userTelephoneKey]} userCity:${value[userCityKey]}"
                )
                withContext(Dispatchers.Main) {
                    loadPreferenceData()
                }
            }
        }
    }

    override fun deletePreferenceUserInfo() {
        Log.i(TAG, "deletePreferenceUserInfo")
        GlobalScope.launch {
            preferencesDataStore.edit {
                it.remove(userIdKey)
                it.remove(userNameKey)
                it.remove(userAgeKey)
                it.remove(userTelephoneKey)
                it.remove(userCityKey)

                withContext(Dispatchers.Main) {
                    loadPreferenceData()
                }
            }
        }
    }

    override fun loadProtoData() {
        Log.i(TAG, "loadProtoData")
        GlobalScope.launch {
            protoDataStore.data
                .catch {
                    mvpView.onLoadProtoDataFailed("load data error")
                }
                .collect {
                    val userInfoString =
                        "userId:${it.id} userName:${it.name} userAge:${it.age} userTel:${it.telephone} userCity:${it.city}"
                    withContext(Dispatchers.Main) {
                        mvpView.onLoadProtoDataSuccess(userInfoString)
                    }
                }
        }
    }

    override fun setProtoUserInfo() {
        Log.i(TAG, "setProtoUserInfo")
        GlobalScope.launch {
            val randomInt = java.util.Random().nextInt(100)
            val randomAge = java.util.Random().nextInt(50)
            protoDataStore.updateData { preference ->
                Log.i(
                    TAG,
                    "userId:${preference.id} userName:${preference.name} userAge:${preference.age} userTel:${preference.telephone} userCity:${preference.city}"
                )
                preference.toBuilder()
                    .setId(randomInt)
                    .setName("User$randomInt")
                    .setAge(randomAge)
                    .setTelephone("136****00$randomInt")
                    .setCity("shanghai$randomInt")
                    .build()
            }
        }
    }

    override fun deleteProtoUserInfo() {
        Log.i(TAG, "deleteProtoUserInfo")
        GlobalScope.launch {
            protoDataStore.updateData { preference ->
                preference.toBuilder()
                    .setId(0)
                    .setName("")
                    .setAge(0)
                    .setTelephone("")
                    .setCity("")
                    .build()
            }
        }
    }

}