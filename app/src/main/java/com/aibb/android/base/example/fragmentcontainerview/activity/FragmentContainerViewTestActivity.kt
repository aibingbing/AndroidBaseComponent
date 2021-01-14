package com.aibb.android.base.example.fragmentcontainerview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.R
import com.aibb.android.base.example.h5.fragment.H5TencentX5LoadFragment

class FragmentContainerViewTestActivity : AppCompatActivity() {
    companion object {
        const val TAG = "FragmentContainerViewTestActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_webview_fragment_activity)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, H5TencentX5LoadFragment::class.java, Bundle().apply {
                putString("url", "https://yuweiguocn.github.io/androidx-fragment/")
            }).commit()
    }
}