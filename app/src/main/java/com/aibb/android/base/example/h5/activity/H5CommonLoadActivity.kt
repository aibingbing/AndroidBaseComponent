package com.aibb.android.base.example.h5.activity

import android.graphics.Bitmap
import android.graphics.Color
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.BuildConfig
import com.aibb.android.base.example.R
import com.blankj.utilcode.util.NetworkUtils
import kotlinx.android.synthetic.main.h5_common_load_activity.*


class H5CommonLoadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "H5CommonLoadActivity"
        const val URL = "http://www.baidu.com"
    }

    private var loadDurationTimeMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.h5_common_load_activity)
        initWebView()
        val url = intent?.getStringExtra("url") ?: URL
        loadUrl(url)
    }

    private fun initWebView() {
        val settings: WebSettings = webView.settings
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
        if (NetworkUtils.isConnected()) {
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
        webView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        webView.background.alpha = 0
        webView.webViewClient = webViewClient
        webView.setHorizontalScrollbarOverlay(true)
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.webChromeClient = webChromeClient
    }

    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            onStartLoading()
            loadDurationTimeMillis = System.currentTimeMillis()
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            //super.onReceivedSslError(view, handler, error)
            view?.loadUrl("about:blank")
        }


        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            onStopLoading()
            calculateLoadDurationTimeMillis()
        }


        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
            return super.shouldOverrideKeyEvent(view, event)
        }


        override fun onLoadResource(view: WebView?, url: String?) {
            super.onLoadResource(view, url)
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "onLoadResource:$url")
            }
        }
    }

    private val webChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            if (!TextUtils.isEmpty(title)) {
                if (title.toLowerCase()
                        .contains("error") || "Webpage not available" == title || "网页无法打开" == title
                ) {
                    view.loadUrl("about:blank")
                }
            }
        }

        override fun onProgressChanged(view: WebView?, progress: Int) {
            onProgressChanged(progress)
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
            if (BuildConfig.DEBUG) {
                Log.e(
                    TAG, "onConsoleMessage:$consoleMessage"
                )
            }
            return super.onConsoleMessage(consoleMessage)
        }
    }

    private fun loadUrl(url: String) {
        Log.e(TAG, "load url: $url")
        webView.loadUrl(url)
        webView.addJavascriptInterface(this, "webView")
    }

    private fun onStopLoading() {
        pbBar.visibility = View.GONE
    }

    private fun onStartLoading() {
        pbBar.visibility = View.VISIBLE
    }

    private fun onProgressChanged(progress: Int) {
        pbBar.progress = progress
    }

    private fun calculateLoadDurationTimeMillis() {
        val duration = System.currentTimeMillis() - loadDurationTimeMillis
        loadingDurationTextView.text = "H5加载时间：${duration}ms"
    }
}