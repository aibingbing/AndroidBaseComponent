 package com.aibb.android.base.example.h5.activity

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.BuildConfig
import com.aibb.android.base.example.R
import com.aibb.android.base.example.h5.cache.WebResourceRequestAdapter
import com.aibb.android.base.example.h5.cache.WebResourceResponseAdapter
import com.blankj.utilcode.utils.NetworkUtils
import com.tencent.smtt.export.external.interfaces.ConsoleMessage
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.*
import kotlinx.android.synthetic.main.h5_tecent_x5_load_activity.*
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst


class H5TencentX5LoadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "H5TencentX5LoadActivity"

        //const val URL = "http://debugtbs.qq.com"
        const val URL =
            "http://carlife-test.zhidaohulian.com/flow-purchase/?spuId=2209&sn=ZD821C1950L01600&iccid=89860319472089765968&userId=1356748089779"
    }

    private var loadDurationTimeMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.h5_tecent_x5_load_activity)
        initWebView()
        loadUrl(URL)
        initX5Info()
    }

    private fun initX5Info() {
        val msg = "X5内核信息：version=${QbSdk.getTbsVersion(this)}, canLoadX5:${QbSdk.canLoadX5(this)}"
        x5InfoTextView.text = msg
    }

    private fun initWebView() {
        val settings: WebSettings = webView.settings
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.defaultTextEncodingName = "utf-8"
        settings.loadsImagesAutomatically = true
        settings.setSupportMultipleWindows(true)
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true
        settings.databaseEnabled = true
        settings.allowFileAccess = true
        settings.setGeolocationEnabled(true);
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        settings.setAppCacheEnabled(true)
        val appCachePath: String = applicationContext.cacheDir.absolutePath
        settings.databaseEnabled = true
        settings.setAppCachePath(appCachePath)
        if (NetworkUtils.isConnected(this)) {
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//        }
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
            p0: WebView?,
            p1: SslErrorHandler?,
            p2: com.tencent.smtt.export.external.interfaces.SslError?
        ) {
            super.onReceivedSslError(p0, p1, p2)
            //super.onReceivedSslError(view, handler, error)
            p0?.loadUrl("about:blank")
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

        override fun shouldOverrideUrlLoading(p0: WebView?, url: String?): Boolean {
            if (url?.startsWith("http") == true) {
                return false
            }
            return true
        }

        override fun shouldInterceptRequest(webview: WebView?, s: String?): WebResourceResponse? {
            return WebResourceResponseAdapter.adapter(
                WebViewCacheInterceptorInst.getInstance().interceptRequest(s)
            )
        }

        override fun shouldInterceptRequest(
            p0: WebView?,
            webResourceRequest: WebResourceRequest?
        ): WebResourceResponse? {
            return WebResourceResponseAdapter.adapter(
                WebViewCacheInterceptorInst.getInstance()
                    .interceptRequest(WebResourceRequestAdapter.adapter(webResourceRequest))
            )
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
        WebViewCacheInterceptorInst.getInstance().loadUrl(url, webView.settings.userAgentString)
        webView.addJavascriptInterface(this, "webView")


        CookieSyncManager.createInstance(this)
        CookieSyncManager.getInstance().sync()
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView?.canGoBack() == true) {
                webView.goBack()
                true
            } else {
                super.onKeyDown(keyCode, event)
            }
        } else {
            super.onKeyDown(keyCode, event)
        }
    }
}