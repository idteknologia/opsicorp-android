package com.opsigo.travelaja.module.home.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class HomeWebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(URL)
        val webView = WebView(this)
        setContentView(webView)
        webView.loadUrl(url)
    }

    companion object {
        const val URL = "URL"
    }
}