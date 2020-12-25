package com.aibb.android.base.example.setup.Initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.facebook.stetho.Stetho

class StethoInitializer : Initializer<Boolean> {

    companion object {
        const val TAG = "StethoInitializer"
    }

    override fun create(context: Context): Boolean {
        Log.i(TAG, "Stetho init")
        Stetho.initializeWithDefaults(context)
        return true
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(LogInitializer::class.java)
    }

}