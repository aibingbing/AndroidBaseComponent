package com.aibb.android.base.example.main.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.ButterKnife
import com.aibb.android.base.example.R
import com.aibb.android.base.example.compose.activity.ComposeTestActivity
import com.aibb.android.base.example.datastore.activity.DataStoreTestActivity
import com.aibb.android.base.example.h5.activity.H5LoadingTestActivity
import com.aibb.android.base.example.hilt.activity.HiltTestActivity
import com.aibb.android.base.example.lazy.activity.MvpViewPagerActivity
import com.aibb.android.base.example.lifecycle.activity.LifecycleTestActivity
import com.aibb.android.base.example.livedata.activity.LiveDataTestActivity
import com.aibb.android.base.example.main.adapter.MainAdapter
import com.aibb.android.base.example.main.pojo.MainModule
import com.aibb.android.base.example.mvp.activity.MvpMainActivity
import com.aibb.android.base.example.network.activity.NetworkServiceTestActivity
import com.aibb.android.base.example.permission.activity.PermissionRequestTestActivity
import com.aibb.android.base.example.room.activity.RoomTestActivity
import com.aibb.android.base.example.setup.activity.SetupTestActivity
import com.aibb.android.base.example.viewmodel.activity.ViewModelTestActivity
import com.aibb.android.base.example.whatif.activity.WhatIfTestActivity
import com.aibb.android.base.example.widget.GridDividerItemDecoration
import com.aibb.android.base.example.workmanager.activity.WorkManagerTestActivity
import com.aibb.android.base.log.LogCollect
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private var gridSpacingItemDecoration: GridDividerItemDecoration? = null
    private val modules = mutableListOf<MainModule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogCollect.i("onCreate")
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initRecyclerView()
        initModules()
    }

    private fun initModules() {
        val mvpModule = MainModule("MvpBase")
        val lazyModule = MainModule("LazyFragment")
        val networkModule = MainModule("RetrofitFactory")
        val hiltModule = MainModule("Hilt")
        val dataStoreModule = MainModule("DataStore")
        val lifeCycleModule = MainModule("LifeCycle")
        val liveDataModule = MainModule("LiveData")
        val viewModelModule = MainModule("ViewModel")
        val roomModule = MainModule("Room")
        val setupModule = MainModule("Setup")
        val workManagerModule = MainModule("WorkManager")
        val whatIfModule = MainModule("WhatIf")
        val permissionRequestModule = MainModule("Permission Request")
        val h5LoadModule = MainModule("H5 Load")
        val composeModule = MainModule("Jetpack Compose")
        modules.add(mvpModule)
        modules.add(lazyModule)
        modules.add(networkModule)
        modules.add(hiltModule)
        modules.add(dataStoreModule)
        modules.add(lifeCycleModule)
        modules.add(liveDataModule)
        modules.add(viewModelModule)
        modules.add(roomModule)
        modules.add(setupModule)
        modules.add(workManagerModule)
        modules.add(whatIfModule)
        modules.add(permissionRequestModule)
        modules.add(h5LoadModule)
        modules.add(composeModule)

        adapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    startActivity(Intent(this, MvpMainActivity::class.java))
                }
                1 -> {
                    startActivity(Intent(this, MvpViewPagerActivity::class.java))
                }
                2 -> {
                    startActivity(Intent(this, NetworkServiceTestActivity::class.java))
                }
                3 -> {
                    startActivity(Intent(this, HiltTestActivity::class.java))
                }
                4 -> {
                    startActivity(Intent(this, DataStoreTestActivity::class.java))
                }
                5 -> {
                    startActivity(Intent(this, LifecycleTestActivity::class.java))
                }
                6 -> {
                    startActivity(Intent(this, LiveDataTestActivity::class.java))
                }
                7 -> {
                    startActivity(Intent(this, ViewModelTestActivity::class.java))
                }
                8 -> {
                    startActivity(Intent(this, RoomTestActivity::class.java))
                }
                9 -> {
                    startActivity(Intent(this, SetupTestActivity::class.java))
                }
                10 -> {
                    startActivity(Intent(this, WorkManagerTestActivity::class.java))
                }
                11 -> {
                    startActivity(Intent(this, WhatIfTestActivity::class.java))
                }
                12 -> {
                    startActivity(Intent(this, PermissionRequestTestActivity::class.java))
                }
                13 -> {
                    startActivity(Intent(this, H5LoadingTestActivity::class.java))
                }
                14 -> {
                    startActivity(Intent(this, ComposeTestActivity::class.java))
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 4)
        recyclerView.layoutManager = gridLayoutManager
        gridSpacingItemDecoration?.also {
            recyclerView.removeItemDecoration(it)
        }
        gridSpacingItemDecoration =
            GridDividerItemDecoration(
                4,
                resources.getDimensionPixelSize(R.dimen.main_module_item_divide),
                resources.getDimensionPixelSize(R.dimen.main_module_item_divide)
            )
        recyclerView.addItemDecoration(gridSpacingItemDecoration!!)

        adapter = MainAdapter(this, modules)
        recyclerView.adapter = adapter
    }
}
