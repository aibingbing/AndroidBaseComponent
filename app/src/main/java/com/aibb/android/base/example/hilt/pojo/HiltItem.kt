package com.aibb.android.base.example.hilt.pojo

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

open class HiltItem : Serializable, MultiItemEntity {
    var id: Long = 0
    var name: String = ""

    companion object {
        const val ItemTypeNewRecord = 1
    }

    override fun getItemType(): Int {
        return ItemTypeNewRecord
    }
}