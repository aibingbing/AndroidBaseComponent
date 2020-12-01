package com.aibb.android.base.example.main

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

data class MainModule(val title: String) : Serializable, MultiItemEntity {

    companion object {
        const val ItemTypeNewRecord = 1
    }

    override fun getItemType(): Int {
        return ItemTypeNewRecord
    }

}