package com.aibb.android.base.example.dokit.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.R
import com.aibb.android.base.example.h5.fragment.H5TencentX5LoadFragment

class DidiDoKitTestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DidiDoKitTestActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_webview_fragment_activity)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, H5TencentX5LoadFragment::class.java, Bundle().apply {
                putString("url", "http://www.dokit.cn")
            }).commit()
    }
}
