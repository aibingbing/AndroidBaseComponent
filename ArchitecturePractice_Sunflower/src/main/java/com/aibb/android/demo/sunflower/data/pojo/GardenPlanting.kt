package com.aibb.android.demo.sunflower.data.pojo

import androidx.room.*
import java.util.*

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/9/8 <br>
 * Desc:        <br>
 */
@Entity(
    tableName = "garden_planting",
    foreignKeys = [ForeignKey(
        entity = Plant::class,
        parentColumns = ["id"],
        childColumns = ["plant_id"]
    )],
    indices = [Index("plant_id")]
)
data class GardenPlanting(
    @ColumnInfo(name = "plant_id")
    val plantId: String,
    @ColumnInfo(name = "plant_date")
    val plantDate: Calendar = Calendar.getInstance(),
    @ColumnInfo
    val lastWateringDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenPlanting: Long = 0
}