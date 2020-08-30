package com.aibb.android.base.example.network.pojo

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
open class GithubRepos : Serializable, MultiItemEntity {
    var id: Long = 0
    var name: String = ""

    companion object {
        const val ItemTypeNewRecord = 1
    }

    override fun getItemType(): Int {
        return ItemTypeNewRecord
    }
}