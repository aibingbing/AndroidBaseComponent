package com.aibb.android.base.example.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aibb.android.base.example.room.dao.UserEntityDao
import com.aibb.android.base.example.room.pojo.UserEntity
import com.aibb.android.base.example.workmanager.dao.WorkerDao
import com.aibb.android.base.example.workmanager.pojo.WorkEntity

@Database(entities = [UserEntity::class, WorkEntity::class], version = 2)
abstract class MyRoomDatabase : RoomDatabase() {
    lateinit var INSTANCE: MyRoomDatabase

    fun initInstance(context: Context): MyRoomDatabase {
        INSTANCE = Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java, "db_room"
        ) //.createFromAsset("database/myapp.db")
            //.fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
        return INSTANCE
    }

    fun getInstance(): MyRoomDatabase {
        return INSTANCE
    }

    abstract fun userDao(): UserEntityDao
    abstract fun workerDao(): WorkerDao

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
//                    "PRIMARY KEY(`id`))")
        }
    }

}