package com.aibb.android.base.example.main.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.ButterKnife
import com.aibb.android.base.example.R
import com.aibb.android.base.example.datastore.activity.DataStoreTestActivity
import com.aibb.android.base.example.hilt.activity.HiltTestActivity
import com.aibb.android.base.example.lazy.activity.MvpViewPagerActivity
import com.aibb.android.base.example.lifecycle.activity.LifecycleTestActivity
import com.aibb.android.base.example.livedata.activity.LiveDataTestActivity
import com.aibb.android.base.example.main.adapter.MainAdapter
import com.aibb.android.base.example.main.pojo.MainModule
import com.aibb.android.base.example.mvp.activity.MvpMainActivity
import com.aibb.android.base.example.network.activity.NetworkServiceTestActivity
import com.aibb.android.base.example.room.activity.RoomTestActivity
import com.aibb.android.base.example.setup.activity.SetupTestActivity
import com.aibb.android.base.example.viewmodel.activity.ViewModelTestActivity
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

        adapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    val intent = Intent(this, MvpMainActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this, MvpViewPagerActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this, NetworkServiceTestActivity::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(this, HiltTestActivity::class.java)
                    startActivity(intent)
                }
                4 -> {
                    val intent = Intent(this, DataStoreTestActivity::class.java)
                    startActivity(intent)
                }
                5 -> {
                    val intent = Intent(this, LifecycleTestActivity::class.java)
                    startActivity(intent)
                }
                6 -> {
                    val intent = Intent(this, LiveDataTestActivity::class.java)
                    startActivity(intent)
                }
                7 -> {
                    val intent = Intent(this, ViewModelTestActivity::class.java)
                    startActivity(intent)
                }
                8 -> {
                    val intent = Intent(this, RoomTestActivity::class.java)
                    startActivity(intent)
                }
                9 -> {
                    val intent = Intent(this, SetupTestActivity::class.java)
                    startActivity(intent)
                }
                10 -> {
                    val intent = Intent(this, WorkManagerTestActivity::class.java)
                    startActivity(intent)
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
