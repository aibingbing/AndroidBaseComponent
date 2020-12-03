package com.aibb.android.base.example.hilt.pojo

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

open class Repository : Serializable, MultiItemEntity {
    var id: Long = 0
    var name: String = ""

    companion object {
        const val itemType = 1
    }

    override fun getItemType(): Int {
        return itemType
    }
}