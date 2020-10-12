package com.aibb.android.base.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.aibb.android.base.example.mvp.MvpMainActivity
import com.aibb.android.base.example.mvp.MvpViewPagerActivity
import com.aibb.android.base.example.mvp.NetworkServiceTestActivity
import com.aibb.android.base.log.LogCollect
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        mvpBtn.setOnClickListener {
            val intent = Intent(this, MvpMainActivity::class.java)
            startActivity(intent)
        }
        mvpLazyFragmentBtn.setOnClickListener {
            val intent = Intent(this, MvpViewPagerActivity::class.java)
            startActivity(intent)
        }
        networkBtn.setOnClickListener {
            val intent = Intent(this, NetworkServiceTestActivity::class.java)
            startActivity(intent)
        }
        LogCollect.i("onCreate")
    }
}
