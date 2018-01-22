package com.cr.gankio.ui.web

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        webView.loadUrl(url)
    }
}