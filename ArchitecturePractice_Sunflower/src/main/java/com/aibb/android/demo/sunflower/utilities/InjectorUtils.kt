package com.aibb.android.demo.sunflower.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.aibb.android.demo.sunflower.api.UnsplashService
import com.aibb.android.demo.sunflower.data.AppDatabase
import com.aibb.android.demo.sunflower.data.repository.GardenPlantingRepository
import com.aibb.android.demo.sunflower.data.repository.PlantRepository
import com.aibb.android.demo.sunflower.data.repository.UnsplashRepository
import com.aibb.android.demo.sunflower.viewmodel.GalleryViewModelFactory
import com.aibb.android.demo.sunflower.viewmodel.GardenPlantingListViewModelFactory
import com.aibb.android.demo.sunflower.viewmodel.PlantDetailViewModelFactory
import com.aibb.android.demo.sunflower.viewmodel.PlantListViewModelFactory

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
object InjectorUtils {

    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).plantDao()
        )
    }

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).gardenPlantingDao()
        )
    }

    fun provideGardenPlantingListViewModelFactory(
        context: Context
    ): GardenPlantingListViewModelFactory {
        return GardenPlantingListViewModelFactory(getGardenPlantingRepository(context))
    }

    fun providePlantListViewModelFactory(fragment: Fragment): PlantListViewModelFactory {
        return PlantListViewModelFactory(getPlantRepository(fragment.requireContext()), fragment)
    }

    fun providePlantDetailViewModelFactory(
        context: Context,
        plantId: String
    ): PlantDetailViewModelFactory {
        return PlantDetailViewModelFactory(
            getPlantRepository(context),
            getGardenPlantingRepository(context), plantId
        )
    }

    fun provideGalleryViewModelFactory(): GalleryViewModelFactory {
        val repository = UnsplashRepository(UnsplashService.create())
        return GalleryViewModelFactory(repository)
    }
}