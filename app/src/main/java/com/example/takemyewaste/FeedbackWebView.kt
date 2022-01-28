package com.example.takemyewaste

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_feedback_web_view.*

class FeedbackWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_web_view)

        val myWebView: WebView = findViewById(R.id.webView1)
        myWebView.loadUrl("https://forms.gle/wvBqGrkDecWugTGR6")
        myWebView.settings.javaScriptEnabled = true

    }
    override fun onBackPressed() {

        if (webView1.canGoBack())
            webView1.goBack()

        else
            super.onBackPressed()
    }
}
