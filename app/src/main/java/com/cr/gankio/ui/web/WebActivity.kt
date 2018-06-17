package com.cr.gankio.ui.web

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebSettings
import com.cr.gankio.R
import kotlinx.android.synthetic.main.activity_web_layout.*

/**
 * Created by RUI CAI on 2017/10/27.
 *
 */
class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_layout)
        val url = intent.getStringExtra("url")
        val settings = webView.settings
        // 开启 JS 支持
        settings.javaScriptEnabled = true
        // 支持屏幕缩放
        settings.setSupportZoom(true)
        // 设置出现缩放工具
        settings.builtInZoomControls = true
        // 不显示webview缩放按钮
        settings.displayZoomControls = false
        // 扩大比例的缩放
        settings.useWideViewPort = true
        //自适应屏幕
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.loadWithOverviewMode = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.loadUrl(url)
    }
}