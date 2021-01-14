package com.aibb.android.base.example.matrix.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.R
import com.aibb.android.base.example.h5.fragment.H5TencentX5LoadFragment

class TencentMatrixTestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TencentMatrixTestActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_webview_fragment_activity)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, H5TencentX5LoadFragment::class.java, Bundle().apply {
                putString("url", "https://github.com/Tencent/matrix#matrix_cn")
            }).commit()
    }
}