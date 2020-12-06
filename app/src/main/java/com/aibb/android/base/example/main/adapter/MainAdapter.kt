package com.aibb.android.base.example.main.adapter

import android.content.Context
import android.graphics.Color
import com.aibb.android.base.example.R
import com.aibb.android.base.example.main.pojo.MainModule
import com.aibb.android.base.example.network.pojo.GithubRepos
import com.aibb.android.base.example.widget.SquareTextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlin.random.Random

class MainAdapter(val context: Context, modules: List<MainModule>) :
    BaseMultiItemQuickAdapter<MainModule, BaseViewHolder>(modules) {

    private var random: Random

    init {
        addItemType(GithubRepos.ItemTypeNewRecord, R.layout.main_module_list_item_layout)
        random = Random(256)
    }

    override fun convert(helper: BaseViewHolder, item: MainModule) {
        val moduleTitleTextView: SquareTextView = helper.getView(R.id.moduleTitle)
        moduleTitleTextView.text = item.title
        moduleTitleTextView.setBackgroundColor(
            Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )
        )
    }
}