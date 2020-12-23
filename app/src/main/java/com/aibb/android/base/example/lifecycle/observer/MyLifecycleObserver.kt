package com.aibb.android.base.example.lifecycle.observer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyLifecycleObserver : LifecycleObserver {

    companion object {
        const val TAG = "MyLifecycleObserver"
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateListen() {
        Log.e(TAG, "lifecycle: onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartListen() {
        Log.e(TAG, "lifecycle: onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeListen() {
        Log.e(TAG, "lifecycle: onResume")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseListen() {
        Log.e(TAG, "lifecycle: onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyListen() {
        Log.e(TAG, "lifecycle: onDestroy")
    }
}