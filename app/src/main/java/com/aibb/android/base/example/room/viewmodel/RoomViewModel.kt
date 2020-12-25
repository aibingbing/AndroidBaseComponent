package com.aibb.android.base.example.room.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aibb.android.base.example.MainApplication
import com.aibb.android.base.example.room.pojo.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {

    companion object {
        const val TAG = "RoomViewModel"
    }

    val userList1: MutableLiveData<List<UserEntity>> by lazy {
        MutableLiveData<List<UserEntity>>().also { liveData ->
            viewModelScope.launch(Dispatchers.IO) {
                liveData.postValue(queryAllUser())
            }
        }
    }

    val userList2: MutableLiveData<List<UserEntity>> by lazy {
        MutableLiveData<List<UserEntity>>().also { liveData ->
            viewModelScope.launch {
                queryAllUserUseFlow()
                    .flowOn(Dispatchers.IO)
                    .collect {
                        liveData.postValue(it)
                    }
            }
        }
    }

    val userList3: MutableLiveData<List<UserEntity>> by lazy {
        MutableLiveData<List<UserEntity>>().also { liveData ->
            viewModelScope.launch {
                queryAllUserDistinctUntilChanged()
                    .flowOn(Dispatchers.IO)
                    .collect {
                        liveData.postValue(it)
                    }
            }
        }
    }

    val userList4: MutableLiveData<List<UserEntity>> by lazy {
        MutableLiveData<List<UserEntity>>().also { liveData ->
            viewModelScope.launch(Dispatchers.IO) {
                liveData.postValue(queryUsersSuspend())
            }
        }
    }

    val userList5 = MainApplication.getInstance().database.userDao().loadAllUsersLiveData()

    val userList6: MutableLiveData<List<UserEntity>> by lazy {
        MutableLiveData<List<UserEntity>>().also { liveData ->
            queryAllUsersUseRxFlowable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    liveData.postValue(it)
                }
        }
    }

    private fun queryAllUser() = MainApplication.getInstance().database.userDao().queryAllUser()
    private fun queryUserByLimit(limit: Int) =
        MainApplication.getInstance().database.userDao().queryUserByLimit(limit)

    private fun queryAllUserUseFlow() =
        MainApplication.getInstance().database.userDao().queryAllUserUseFlow()

    private fun queryAllUserDistinctUntilChanged() =
        MainApplication.getInstance().database.userDao().queryAllUserDistinctUntilChanged()

    private suspend fun queryUsersSuspend() =
        MainApplication.getInstance().database.userDao().queryAllUsersSuspend()

    private fun queryAllUsersUseRxFlowable() =
        MainApplication.getInstance().database.userDao().queryAllUsersUseRxFlowable()

    private fun insertUserEntity(user: UserEntity) =
        MainApplication.getInstance().database.userDao().insertAll(user)

    private fun updateUserEntity(user: UserEntity) =
        MainApplication.getInstance().database.userDao().updateUsers(user)

    private fun deleteUserEntity(user: UserEntity) =
        MainApplication.getInstance().database.userDao().delete(user)

    fun insertUserInterval() {
        viewModelScope.launch(Dispatchers.IO) {
            flow<Int> {
                List(1000) {
                    emit(it)
                    delay(200)
                }
            }.flowOn(Dispatchers.IO)
                .onCompletion {
                    Log.i(TAG, "insertUserInterval onCompletion")
                }
                .collect {
                    val u = UserEntity(it, "UserName$it", it, "city$it")
                    Log.i(TAG, "insertUser: UserEntity$it")
                    insertUserEntity(u)
                }
        }
    }

    fun updateUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            flow<UserEntity> {
                queryUserByLimit(30).forEach {
                    emit(it)
                    delay(100)
                }
            }.flowOn(Dispatchers.IO)
                .onCompletion {
                    Log.i(TAG, "updateUsers onCompletion")
                }
                .collect {
                    Log.i(TAG, "updateUser: $it")
                    updateUserEntity(it)
                }
        }
    }

    fun deleteUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            flow<UserEntity> {
                queryUserByLimit(50).forEach {
                    emit(it)
                    delay(100)
                }
            }.flowOn(Dispatchers.IO)
                .onCompletion {
                    Log.i(TAG, "deleteUsers onCompletion")
                }
                .collect {
                    Log.i(TAG, "deleteUser: $it")
                    deleteUserEntity(it)
                }
        }
    }
}