package com.aibb.android.demo.sunflower.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aibb.android.demo.sunflower.data.pojo.Plant

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/9 <br>
 * Desc:        <br>
 */
@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY name")
    fun getPlants(): LiveData<List<Plant>>

    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): LiveData<List<Plant>>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlant(plantId: String): LiveData<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Plant>)
}