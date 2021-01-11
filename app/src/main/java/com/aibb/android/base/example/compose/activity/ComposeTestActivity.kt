package com.aibb.android.base.example.compose.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Clip
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.themeTextStyle
import androidx.ui.material.withOpacity
import androidx.ui.res.imageResource
import androidx.ui.text.style.TextOverflow
import androidx.ui.tooling.preview.Preview
import com.aibb.android.base.example.R

class ComposeTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                crossAxisSize = LayoutSize.Expand,
                modifier = Spacing(16.dp)
            ) {
                Text("Hello, Compose !")
                greeting("Test")
                newsStory()
            }
        }
    }

    @Composable
    fun greeting(msg: String) {
        Text(text = "Hello $msg!")
    }

    /**
     * @Preview 条件
     * 1。函数没有参数
     * 2。在函数前面添加@Preview注解
     *
     * 当前Jetpack Compose 为开发者预览版，Android Studio 必须是Canary版本
     */
    @Preview
    @Composable
    fun newsStory() {
        val image = +imageResource(R.mipmap.img_compose_test)
        MaterialTheme {
            Column(
                crossAxisSize = LayoutSize.Expand,
                modifier = Spacing(16.dp)
            ) {
                Container(expanded = true, height = 180.dp) {
                    Clip(shape = RoundedCornerShape(10.dp)) {
                        // 显示图片
                        DrawImage(image)
                    }
                }
                HeightSpacer(height = 20.dp)

                Text("我超❤️JetPack Compose的！", style = +themeTextStyle { h5 })
                Text("Android技术杂货铺", style = (+themeTextStyle { body1 }).withOpacity(0.87f))
                Text("依然范特西", style = (+themeTextStyle { body2 }).withOpacity(0.5f))
                Text(
                    text = (1..20).joinToString(",") { "我是一段很长的文字$it" },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = (+themeTextStyle { body2 }).withOpacity(0.4f)
                )
            }
        }
    }

    @Preview
    @Composable
    fun defaultPreview() {
        MaterialTheme {
            Text("Hello,World")
        }
    }
}