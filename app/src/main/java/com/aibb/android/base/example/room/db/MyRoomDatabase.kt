package com.aibb.android.base.example.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aibb.android.base.example.room.dao.UserEntityDao
import com.aibb.android.base.example.room.pojo.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserEntityDao

    lateinit var INSTANCE: MyRoomDatabase

    fun initInstance(context: Context): MyRoomDatabase {
        INSTANCE = Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java, "db_room"
        )
            //                .createFromAsset("database/myapp.db")
            //                .fallbackToDestructiveMigration()
            .build()
        return INSTANCE
    }

    fun getInstance(): MyRoomDatabase {
        return INSTANCE
    }

}