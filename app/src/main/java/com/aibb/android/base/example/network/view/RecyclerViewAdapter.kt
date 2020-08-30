package com.aibb.android.base.example.network.view

import android.content.Context
import android.widget.TextView
import com.aibb.android.base.example.R
import com.aibb.android.base.example.network.pojo.GithubRepos
import com.aibb.android.base.example.network.pojo.GithubRepos.Companion.ItemTypeNewRecord
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/30 <br>
 * Desc:        <br>
 */
class RecyclerViewAdapter(val context: Context, reposList: List<GithubRepos>) :
    BaseMultiItemQuickAdapter<GithubRepos, BaseViewHolder>(reposList) {

    init {
        addItemType(ItemTypeNewRecord, R.layout.net_service_list_item_layout)
    }

    override fun convert(helper: BaseViewHolder, item: GithubRepos) {
        val nameText: TextView = helper.getView(R.id.name)
        nameText.text = item.name
    }
}