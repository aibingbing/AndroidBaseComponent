package com.aibb.android.base.example.lifecycle.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.aibb.android.base.example.R
import com.aibb.android.base.example.lifecycle.observer.MyLifecycleObserver
import kotlinx.android.synthetic.main.lifecycle_test_activity.*

class LifecycleTestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LifecycleTestActivity"
    }

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.lifecycle_test_activity)

        closeBtn.setOnClickListener {
            finish()
        }

        goToOwnLifecyclePageBtn.setOnClickListener {
            startActivity(Intent(this, LifecycleTestActivity1::class.java))
        }

        lifecycle.addObserver(MyLifecycleObserver())
    }

    override fun onResume() {
        super.onResume()
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
        }
    }
}