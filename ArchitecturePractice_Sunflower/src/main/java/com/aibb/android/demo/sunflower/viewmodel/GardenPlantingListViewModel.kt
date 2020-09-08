package com.aibb.android.demo.sunflower.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aibb.android.demo.sunflower.data.pojo.PlantAndGardenPlantings
import com.aibb.android.demo.sunflower.data.repository.GardenPlantingRepository

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
class GardenPlantingListViewModel internal constructor(
    gardenPlantingRepository: GardenPlantingRepository
) : ViewModel() {
    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens()
}