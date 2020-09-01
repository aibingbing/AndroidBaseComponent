package com.aibb.android.base.example.mvp

import androidx.fragment.app.Fragment
import com.aibb.android.base.example.R
import com.aibb.android.base.example.base.MyBaseMvpActivity
import com.aibb.android.base.example.mvp.adapter.ViewPagerAdapter
import com.aibb.android.base.example.mvp.fragment.LazyMvpFragment1
import com.aibb.android.base.example.mvp.fragment.LazyMvpFragment2
import kotlinx.android.synthetic.main.mvp_viewpager_activity.*

class MvpViewPagerActivity : MyBaseMvpActivity() {

    override fun getLayoutId(): Int {
        return R.layout.mvp_viewpager_activity
    }

    override fun initialize() {
        val fragments = mutableListOf<Fragment>().apply {
            add(LazyMvpFragment1())
            add(LazyMvpFragment2())
        }
        val tabTitles = mutableListOf<String>().apply {
            add("LazyFragment1")
            add("LazyFragment2")
        }
        viewPager.apply {
            adapter = ViewPagerAdapter(
                fragmentManager = supportFragmentManager,
                fragments = fragments,
                titles = tabTitles
            )
        }
        tabLayout.setupWithViewPager(viewPager)
    }
}