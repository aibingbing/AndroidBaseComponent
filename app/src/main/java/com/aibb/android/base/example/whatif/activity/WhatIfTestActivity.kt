package com.aibb.android.base.example.whatif.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.BuildConfig
import com.aibb.android.base.example.R
import com.aibb.android.base.example.network.pojo.GithubRepos
import com.blankj.utilcode.utils.NetworkUtils
import com.skydoves.whatif.*
import com.skydoves.whatif_android.whatIfHasExtras
import com.skydoves.whatif_android.whatIfHasSerializableExtra
import kotlinx.android.synthetic.main.what_if_test_activity.*

class WhatIfTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.what_if_test_activity)
        initWebView()
        testWhatIf()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val settings: WebSettings = webView.getSettings()
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportZoom(false)
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.defaultTextEncodingName = "utf-8"
        settings.loadsImagesAutomatically = true
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true
        settings.databaseEnabled = true
        settings.setAppCacheEnabled(true)
        val appCachePath: String = applicationContext.cacheDir.absolutePath
        settings.databaseEnabled = true
        settings.setAppCachePath(appCachePath)
        if (NetworkUtils.isConnected(this)) {
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        webView.background.alpha = 0
//        webView.setWebViewClient(mWebViewClient)
        webView.setHorizontalScrollbarOverlay(true)
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                }
            }
        })
        webView.loadUrl("https://github.com/skydoves/whatif");
    }

    private fun log(str: String) {
        Log.i("WhatIf", str)
    }

    private fun testWhatIf() {
        var nullableBoolean: Boolean? = true
        whatIf(nullableBoolean) {
            log("not-null and true : $nullableBoolean")
        }

        nullableBoolean.whatIf {
            log("not-null and true : $nullableBoolean")
        }

        whatIf(
            given = nullableBoolean,
            whatIf = { log("not-null and true : $nullableBoolean") },
            whatIfNot = { log("null or false : $nullableBoolean") }
        )

        nullableBoolean = false
        nullableBoolean.whatIf(
            whatIf = { log("not-null and true : $nullableBoolean") },
            whatIfNot = { log("null or false : $nullableBoolean") }
        )

//        val balloon = Balloon.Builder(baseContext)
//            .setArrowSize(10)
//            .setArrowVisible(true)
//            .whatIf(nullableBoolean) { setTextColor(Color.YELLOW) }
//            .whatIf(nullableBoolean, { setText("Hello, whatIf") }, { setText("Good-Bye whatIf") })
//            .setWidthRatio(1.0f)
//            .build()

        val nullableObject: GithubRepos? = GithubRepos()
        nullableObject.whatIfNotNull {
            log("$it is not null")
        }

        nullableObject.whatIfNotNull(
            whatIf = { log("$it is not null.") },
            whatIfNot = { log(" is null.") }
        )

//        serializable.whatIfNotNullAs<Poster>(
//             whatIf = { poster -> log(poster.name) },
//             whatIfNot = {
//                  // do something
//             })

        val nullableString: String? = "NotNullOrEmpty"
        nullableString.whatIfNotNullOrEmpty {
            log("$it is not null and not empty")
        }

        val nullableList = mutableListOf<Int>()
        nullableList.whatIfNotNullOrEmpty {
            log("list $it is not null and not empty")
        }
        nullableList.whatIfNotNullOrEmpty(
            whatIf = { log("list $it is not null and not empty") },
            whatIfNot = { log("list is null or empty") }
        )

        val nullableArray = emptyArray<Int>()
        nullableArray.whatIfNotNullOrEmpty {
            log("$it is not null and not empty")
        }

//        val length: Int = nullableString.whatIfMap(
//            whatIf = { nullableString?.length },
//            whatIfNot = {
//                log("$nullableString, nullableString is null.")
//                -1
//            }
//        )

        nullableBoolean.whatIfElse {
            log("nullableBoolean is not null and false")
        }

        nullableBoolean.whatIfAnd(5 > 4) {
            log("nullableBoolean is true and the predicate is also true")
        }

        nullableBoolean.whatIfOr(4 > 6) {
            log("nullableBoolean is true or the predicate is true")
        }

        var foo: String? = null
        this.whatIfHasExtras {
            foo = "${it.getString("foo")}"
        }

        this.whatIfHasExtras(
            whatIf = { foo = "${it.getString("foo")}" },
            whatIfNot = { log("intent extras is null or empty.") }
        )

        whatIfHasSerializableExtra<GithubRepos>("repos") { repos ->
        }
//        whatIfHasParcelableExtra<GithubRepos>("repos") { repos ->
//        }

        // in fragmentd
//        whatIfNotNullContext {
//
//        }
//        whatIfNotNullArguments {
//            it.getString("name").whatIfNotNull {
//                log("my name is $it")
//            }
//        }

//        whatIfNotNullActivity { activity ->
//            activity.supportFragmentManager.addOnBackStackChangedListener {
//                // .. //
//            }
//        }
    }
}
