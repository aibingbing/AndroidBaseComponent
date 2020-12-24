package com.aibb.android.base.example.viewmodel.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aibb.android.base.example.livedata.pojo.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class ViewModelViewModel : ViewModel() {
    companion object {
        const val TAG = "ViewModelViewModel"
    }

    val users = mutableListOf<User>()

    val userList: MutableLiveData<List<User>> by lazy {
        Log.i(TAG, "init UserListLiveData")
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }

    private fun loadUsers() {
        Log.i(TAG, "loadUsers")
        viewModelScope.launch {
            flow<Int> {
                List(10000) {
                    emit(it)
                    delay(300)
                }
            }.map {
                val u = User(it, "test$it", it)
                users.add(u)
                u
            }.onCompletion {
                Log.i(TAG, "loadUsers completion")
            }.collect {
                userList.postValue(users)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared")
    }
}