package com.aibb.android.base.example.hilt.adapter

import android.widget.TextView
import com.aibb.android.base.example.R
import com.aibb.android.base.example.hilt.pojo.Repository
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class HiltAdapter(repositorys: List<Repository>) :
    BaseMultiItemQuickAdapter<Repository, BaseViewHolder>(repositorys) {

    init {
        addItemType(Repository.itemType, R.layout.hilt_list_item_layout)
    }

    override fun convert(helper: BaseViewHolder, item: Repository) {
        val nameText: TextView = helper.getView(R.id.name)
        nameText.text = item.name
    }
}