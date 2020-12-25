package com.aibb.android.base.example.setup.Initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.aibb.android.base.example.BuildConfig
import com.aibb.android.base.log.LogCollect

class LogInitializer : Initializer<Boolean> {
    companion object {
        const val TAG = "LogInitializer"
    }

    override fun create(context: Context): Boolean {
        Log.i(TAG, "Log init")
        LogCollect.init(
            context,
            LogCollect.newConfig()
                .logLevel(if (BuildConfig.DEBUG) LogCollect.DEBUG else LogCollect.INFO)
                .enableFileLog(true)
                .enableConsoleLog(BuildConfig.DEBUG)
                .fileLogMaxAliveTime((5 * 24 * 60 * 60).toLong())
        )
        return true
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(CrashCatchInitializer::class.java)
    }
}