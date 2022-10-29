package com.example.muscleexercises

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

class WebActivity : AppCompatActivity() {

    var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val sharedPreferences = getSharedPreferences(Data.preferences, Context.MODE_PRIVATE)
        val url = sharedPreferences.getString(Data.url, "") ?: ""

        webView = findViewById(R.id.webView)

        webView?.settings?.allowContentAccess= true

        if (savedInstanceState == null) {
            webView?.webViewClient = WebViewClient()
            webView?.loadUrl(url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (savedInstanceState != null) {
            webView?.saveState(savedInstanceState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView?.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        }
    }
}