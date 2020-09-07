package com.aibb.android.demo.sunflower.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.aibb.android.demo.sunflower.R
import com.aibb.android.demo.sunflower.databinding.ActivityGardenBinding

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/6 <br>
 * Desc:        <br>
 */
class GardenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this,
            R.layout.activity_garden
        )
    }
}