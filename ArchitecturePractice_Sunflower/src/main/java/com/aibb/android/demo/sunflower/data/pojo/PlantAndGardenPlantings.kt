package com.aibb.android.demo.sunflower.data.pojo

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
data class PlantAndGardenPlantings(
    @Embedded
    val plant: Plant,
    @Relation(parentColumn = "id", entityColumn = "plant_id")
    val gardenPlantings: List<GardenPlanting> = emptyList()
)