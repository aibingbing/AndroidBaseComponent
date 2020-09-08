package com.aibb.android.demo.sunflower.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.aibb.android.demo.sunflower.data.AppDatabase
import com.aibb.android.demo.sunflower.data.repository.GardenPlantingRepository

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
object InjectorUtils {

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).gardenPlantingDao()
        )
    }

}