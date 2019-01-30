package com.sentosh1ne.linkedinapihelperv2.ui

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sentosh1ne.linkedinapihelperv2.R
import com.sentosh1ne.linkedinapihelperv2.data.api.AuthApi
import com.sentosh1ne.linkedinapihelperv2.data.session.AppConfig
import kotlinx.android.synthetic.main.auth_activity.*

internal class LinkedInAuthActivity : AppCompatActivity() {
    private var scope: String? = null

    private var appConfig: AppConfig? = null

    private lateinit var authApi: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)
        handleExtras(savedInstanceState ?: intent.extras)

        with(authWebView.settings) {
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            loadWithOverviewMode = true
            useWideViewPort = true
            javaScriptEnabled = true
        }

        authApi = AuthApi()
        initWebClient()

        if (appConfig == null || scope == null) {
            Log.i(LinkedInAuthActivity::class.simpleName, "Scope or AppConfig was not provided")
            setResult(Activity.RESULT_CANCELED)
            finish()
        } else {
            authWebView.loadUrl(authApi.buildCodeRequestUrl(scope!!, appConfig!!))
        }
    }

    private fun initWebClient() {
        authWebView.webViewClient = object : WebViewClient() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                //todo get token and finish with activity result
                val code = request?.url?.getQueryParameter("code")
                var state = request?.url?.getQueryParameter("state")


                code?.let {
                    authApi.getToken(appConfig!!, code, state)
                }

                //todo save session
                return false
            }


            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                val parsedUri = Uri.parse(url)
                val code = parsedUri.getQueryParameter("code")
                var state = parsedUri.getQueryParameter("state")

                if (state == null) {
                    state = ""
                }

                authApi.getToken(appConfig!!, state)

                return false
            }
        }
    }


    private fun handleExtras(bundle: Bundle?) {
        bundle?.let {
            appConfig = it.getParcelable("appConfig")
            scope = it.getString("scope")
        }
    }
}