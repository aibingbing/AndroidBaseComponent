package com.aibb.android.base.example.h5.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.R
import kotlinx.android.synthetic.main.h5_loading_test_activity.*

class H5LoadingTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.h5_loading_test_activity)

        commonWebViewLoadBtn.setOnClickListener {
            startActivity(Intent(this, H5CommonLoadActivity::class.java))
        }
        tencentX5LoadBtn.setOnClickListener {
            startActivity(Intent(this, H5TencentX5LoadActivity::class.java))
        }
    }
}