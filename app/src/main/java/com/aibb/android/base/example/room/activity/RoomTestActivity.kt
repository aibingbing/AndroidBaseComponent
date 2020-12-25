package com.aibb.android.base.example.room.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.aibb.android.base.example.R
import com.aibb.android.base.example.room.pojo.UserEntity
import com.aibb.android.base.example.room.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.room_test_activity.*
import kotlinx.coroutines.cancel

class RoomTestActivity : AppCompatActivity() {

    private val viewModel: RoomViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_test_activity)

        val userList1Observer = Observer<List<UserEntity>> {
            text1.text = "queryAllUser(): User Table Rows = ${it.size}"
        }
        viewModel.userList1.observe(this, userList1Observer)

        val userList2Observer = Observer<List<UserEntity>> {
            text2.text = "queryAllUserUseFlow(): User Table Rows = ${it.size}"
        }
        viewModel.userList2.observe(this, userList2Observer)

        val userList3Observer = Observer<List<UserEntity>> {
            text3.text = "queryAllUserDistinctUntilChanged(): User Table Rows = ${it.size}"
        }
        viewModel.userList3.observe(this, userList3Observer)

        val userList4Observer = Observer<List<UserEntity>> {
            text4.text = "queryUsersSuspend(): User Table Rows = ${it.size}"
        }
        viewModel.userList4.observe(this, userList4Observer)

        val userList5Observer = Observer<List<UserEntity>> {
            text5.text = "loadAllUsersLiveData(): User Table Rows = ${it.size}"
        }
        viewModel.userList5.observe(this, userList5Observer)

        val userList6Observer = Observer<List<UserEntity>> {
            text6.text = "queryAllUsersUseRxFlowable(): User Table Rows = ${it.size}"
        }
        viewModel.userList6.observe(this, userList6Observer)

        insertUserBtn.setOnClickListener {
            viewModel.insertUserInterval()
        }

        stopInsertUserBtn.setOnClickListener {
            viewModel.viewModelScope.cancel()
        }

        updateUserBtn.setOnClickListener {
            viewModel.updateUsers()
        }

        deleteUserBtn.setOnClickListener {
            viewModel.deleteUsers()
        }
    }
}