package com.aibb.android.demo.sunflower.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aibb.android.demo.sunflower.data.pojo.GardenPlanting
import com.aibb.android.demo.sunflower.data.pojo.PlantAndGardenPlantings

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
@Dao
interface GardenPlantingDao {
    @Query("select * from garden_planting")
    fun getGardenPlantings(): LiveData<List<GardenPlanting>>

    @Query("select exists (select 1 from garden_planting where plant_id = :plantId limit 1)")
    fun isPlanted(plantId: String): LiveData<Boolean>

    @Transaction
    @Query("select * from plants where id in (select distinct(plant_id) from garden_planting)")
    fun getPlantedGardens(): LiveData<List<PlantAndGardenPlantings>>

    @Insert
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting): Long

    @Delete
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlanting)
}