package com.aibb.android.base.example.hilt.adapter

import android.graphics.Color
import com.aibb.android.base.example.R
import com.aibb.android.base.example.hilt.pojo.HiltItem
import com.aibb.android.base.example.widget.SquareTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import javax.inject.Inject
import kotlin.random.Random

class HiltAdapter @Inject constructor(): BaseQuickAdapter<HiltItem, BaseViewHolder>(R.layout.hilt_list_item_layout) {

    private var random: Random = Random(256)

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

//    @Module
//    @InstallIn(ActivityComponent::class)
//    object HiltAdapterModule {
//        @Provides
//        fun provideHiltAdapter(hiltItems: List<HiltItem>): HiltAdapter {
//            return HiltAdapter(hiltItems)
//        }
//    }
}