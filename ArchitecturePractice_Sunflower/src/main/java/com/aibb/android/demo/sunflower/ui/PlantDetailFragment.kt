package com.aibb.android.demo.sunflower.ui

import androidx.fragment.app.Fragment
import com.aibb.android.demo.sunflower.data.pojo.Plant

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
class PlantDetailFragment :Fragment(){

//    private val args:PlantDetail

    interface Callback {
        fun add(plant: Plant?)
    }
}