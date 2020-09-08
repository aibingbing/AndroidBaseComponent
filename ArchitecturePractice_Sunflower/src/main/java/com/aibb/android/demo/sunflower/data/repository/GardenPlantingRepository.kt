package com.aibb.android.demo.sunflower.data.repository

import com.aibb.android.demo.sunflower.data.dao.GardenPlantingDao
import com.aibb.android.demo.sunflower.data.pojo.GardenPlanting

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
class GardenPlantingRepository private constructor(
    private val gardenPlantingDao: GardenPlantingDao
) {
    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

    companion object {
        @Volatile
        private var instance: GardenPlantingRepository? = null

        fun getInstance(gardenPlantingDao: GardenPlantingDao) =
            instance ?: synchronized(this) {
                instance ?: GardenPlantingRepository(gardenPlantingDao).also { instance = it }
            }
    }
}

