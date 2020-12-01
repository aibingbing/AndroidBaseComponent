package com.aibb.android.base.example.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.ButterKnife
import com.aibb.android.base.example.R
import com.aibb.android.base.example.mvp.MvpMainActivity
import com.aibb.android.base.example.mvp.MvpViewPagerActivity
import com.aibb.android.base.example.mvp.NetworkServiceTestActivity
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
        modules.add(mvpModule)
        modules.add(lazyModule)
        modules.add(networkModule)
        modules.add(hiltModule)
        modules.add(dataStoreModule)

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
                    val intent = Intent(this, NetworkServiceTestActivity::class.java)
                    startActivity(intent)
                }
                4 -> {
                    val intent = Intent(this, NetworkServiceTestActivity::class.java)
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
        gridSpacingItemDecoration = GridDividerItemDecoration(
            4,
            resources.getDimensionPixelSize(R.dimen.main_module_item_divide),
            resources.getDimensionPixelSize(R.dimen.main_module_item_divide)
        )
        recyclerView.addItemDecoration(gridSpacingItemDecoration!!)

        adapter = MainAdapter(this, modules)
        recyclerView.adapter = adapter
    }
}
