package com.aibb.android.base.example.workmanager.dao

import androidx.room.*
import com.aibb.android.base.example.workmanager.pojo.WorkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkerDao {

    @Query("select * from t_work")
    fun queryAllWorkFlow(): Flow<List<WorkEntity>>

    @Query("delete from t_work")
    suspend fun deleteAllWork(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWorker(vararg workers: WorkEntity)

    @Delete
    fun delete(w: WorkEntity)
}