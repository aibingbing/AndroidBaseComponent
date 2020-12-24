package com.aibb.android.base.example.livedata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aibb.android.base.example.livedata.pojo.User

class UserViewModel : ViewModel() {
    val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>()
    }
    val toastMsg: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val userIds: MutableLiveData<List<Int>> by lazy {
        MutableLiveData<List<Int>>()
    }
    val transformationUsers = Transformations.switchMap(userIds) { ids ->
        val tempUsers = ids.map { id ->
            User(id, "User$id", id)
        }
        MutableLiveData<List<User>>(tempUsers)
    }
}