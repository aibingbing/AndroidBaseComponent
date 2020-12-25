package com.aibb.android.base.example.setup.Initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.aibb.android.base.log.LogCollect

class CrashCatchInitializer : Initializer<Boolean> {
    companion object {
        const val TAG = "CrashCatchInitializer"
    }

    override fun create(context: Context): Boolean {
        Log.i(TAG, "Crash init")
        Thread.currentThread().uncaughtExceptionHandler =
            Thread.UncaughtExceptionHandler { _, e ->
                LogCollect.e("Crash", e.localizedMessage)
                Log.e("Crash", e.localizedMessage)
            }
        return true
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}