package com.aibb.android.base.example.livedata.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aibb.android.base.example.R
import com.aibb.android.base.example.livedata.pojo.User
import com.aibb.android.base.example.livedata.viewmodel.UserViewModel
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.livedata_test_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class LiveDataTestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LiveDataTestActivity"
    }

    private val viewModel: UserViewModel by viewModels()
    private val users: MutableList<User> = mutableListOf()

    // test Transformations.switchMap
    private val userIds: MutableList<Int> = mutableListOf()
    // Java 初始化方式
    // UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.livedata_test_activity)

        mockUserBtn.setOnClickListener {
//            mockCreateUser()
            mockCreateUser1()
        }

        val usersObserver = Observer<List<User>> { users ->
            userCountText.text = "当前User总数：${users.size}"
        }
        viewModel.users.observe(this, usersObserver)
        val toastObserver = Observer<String> {
            ToastUtils.showLong(it)
        }
        viewModel.toastMsg.observe(this, toastObserver)
        val transformationUserObserver = Observer<List<User>> {
            userCountText.text = "当前User总数(使用Transformation)：${it.size}"
        }
        viewModel.transformationUsers.observe(this, transformationUserObserver)
    }

    private fun mockCreateUser() {
        GlobalScope.launch(Dispatchers.IO) {
            flow {
                List(50) {
                    emit(it)
                    delay(300)
                }
            }.map {
                val u = User(it, "User$it", it)
                users.add(u)
                u
            }.onCompletion {
                viewModel.toastMsg.postValue("Mock Add Users Complete")
            }.collect {
                viewModel.users.postValue(users)
            }
        }
    }

    private fun mockCreateUser1() {
        GlobalScope.launch(Dispatchers.IO) {
            flow {
                List(50) {
                    emit(it)
                    userIds.add(it)
                    delay(500)
                }
            }.onCompletion {
                viewModel.toastMsg.postValue("Mock Add Users use Transformation type Complete")
            }.collect {
                viewModel.userIds.postValue(userIds)
            }
        }
    }
}