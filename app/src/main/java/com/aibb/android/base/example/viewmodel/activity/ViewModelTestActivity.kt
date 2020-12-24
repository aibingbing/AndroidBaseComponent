package com.aibb.android.base.example.viewmodel.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aibb.android.base.example.R
import com.aibb.android.base.example.livedata.pojo.User
import com.aibb.android.base.example.viewmodel.viewmodel.SavedStateViewModel
import com.aibb.android.base.example.viewmodel.viewmodel.ViewModelViewModel
import kotlinx.android.synthetic.main.viewmodel_test_activity.*

class ViewModelTestActivity : AppCompatActivity() {

    private val viewModel: ViewModelViewModel by viewModels()

    //这些值会在进程被系统终止后继续保留，并通过同一对象保持可用状态
    private val savedStateViewModel: SavedStateViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewmodel_test_activity)

        val userListObserver = Observer<List<User>> {
            userCountText.text = "当前UserList size:${it.size}"
        }
        viewModel.userList.observe(this, userListObserver)
    }

    // Fragment之间共享数据
    // private val model: SharedViewModel by activityViewModels()

}