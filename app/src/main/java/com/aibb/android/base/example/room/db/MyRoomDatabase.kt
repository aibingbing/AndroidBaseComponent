package com.aibb.android.base.example.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aibb.android.base.example.room.dao.UserEntityDao
import com.aibb.android.base.example.room.pojo.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserEntityDao
}