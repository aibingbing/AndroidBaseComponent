package com.aibb.android.base.example.hilt.adapter

import android.graphics.Color
import com.aibb.android.base.example.R
import com.aibb.android.base.example.hilt.pojo.HiltItem
import com.aibb.android.base.example.widget.SquareTextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlin.random.Random

class HiltAdapter(hiltItems: List<HiltItem>) :
    BaseMultiItemQuickAdapter<HiltItem, BaseViewHolder>(hiltItems) {

    private var random: Random

    init {
        addItemType(HiltItem.ItemTypeNewRecord, R.layout.hilt_list_item_layout)
        random = Random(256)
    }

    override fun convert(helper: BaseViewHolder, item: HiltItem) {
        val nameText: SquareTextView = helper.getView(R.id.name)
        nameText.text = item.name
        nameText.setBackgroundColor(
            Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )
        )
    }
}