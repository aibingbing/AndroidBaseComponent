package com.aibb.android.base.example.setup.Initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.aibb.android.base.example.MainApplication
import com.aibb.android.base.example.room.db.MyRoomDatabase

class RoomInitializer : Initializer<MyRoomDatabase> {
    companion object {
        const val TAG = "RoomInitializer"
    }

    override fun create(context: Context): MyRoomDatabase {
        Log.i(TAG, "Room init")
        return MainApplication.initDatabase(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(StethoInitializer::class.java)
    }
}