package com.aibb.android.base.example.boostmultidex.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.R
import com.aibb.android.base.example.h5.activity.H5CommonLoadActivity
import kotlinx.android.synthetic.main.boost_multidex_test_activity.*

class BoostMultiDexTestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BoostMultiDexTestActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boost_multidex_test_activity)
        initView()
    }

    private fun initView() {
        boostMultiDexBtn1.setOnClickListener {
            startWebActivityByUrl("https://mp.weixin.qq.com/s?__biz=MzUxMzcxMzE5Ng==&mid=2247493879&idx=1&sn=653ac0a8d0e33cc3faea6659b25d0398&scene=21#wechat_redirect")
        }
        boostMultiDexBtn2.setOnClickListener {
            startWebActivityByUrl("https://mp.weixin.qq.com/s?__biz=MzI1MzYzMjE0MQ==&mid=2247485530&idx=1&sn=c6f92a614829215d13aec273cbd1022a&chksm=e9d0c3b8dea74aaed9a458e9711d989c780a87d635e377583cc377cd3553d0a8ccbe4aeaa857&token=1566092111&lang=zh_CN&scene=21#wechat_redirect")
        }
        boostMultiDexBtn3.setOnClickListener {
            startWebActivityByUrl("https://mp.weixin.qq.com/s?__biz=MzI1MzYzMjE0MQ==&mid=2247485851&idx=1&sn=dc97b4382292c2b054512160f1c48460&scene=21#wechat_redirect")
        }
    }

    private fun startWebActivityByUrl(url: String) {
        val intent = Intent(this, H5CommonLoadActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

}