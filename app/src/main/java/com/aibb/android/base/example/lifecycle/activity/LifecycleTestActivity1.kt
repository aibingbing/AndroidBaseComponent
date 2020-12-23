package com.aibb.android.base.example.lifecycle.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.aibb.android.base.example.R
import com.aibb.android.base.example.lifecycle.observer.MyLifecycleObserver
import kotlinx.android.synthetic.main.lifecycle_test_activity.*

class LifecycleTestActivity1 : Activity(), LifecycleOwner {

    companion object {
        const val TAG = "LifecycleTestActivity1"
    }

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(R.layout.lifecycle_test_activity1)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        lifecycle.addObserver(MyLifecycleObserver())

        closeBtn.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}