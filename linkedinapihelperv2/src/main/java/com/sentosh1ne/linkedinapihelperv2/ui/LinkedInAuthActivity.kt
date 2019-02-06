package com.sentosh1ne.linkedinapihelperv2.ui

import android.app.Activity
import android.content.Intent
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
import com.sentosh1ne.linkedinapihelperv2.data.session.SessionManager
import kotlinx.android.synthetic.main.auth_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONException

internal class LinkedInAuthActivity : AppCompatActivity() {
    private var scope: String? = null

    private var state: String? = null

    private lateinit var appConfig: AppConfig

    private lateinit var authApi: AuthApi

    private lateinit var sessionManager: SessionManager

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
        sessionManager = SessionManager(this)
        initWebClient()

        if (!::appConfig.isInitialized || scope == null) {
            Log.i(LinkedInAuthActivity::class.java.simpleName, "Scope or AppConfig was not provided")
            setResult(Activity.RESULT_CANCELED)
            finish()
        } else {
            authWebView.loadUrl(authApi.buildCodeRequestUrl(scope!!, appConfig))
        }
    }

    private fun initWebClient() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            authWebView.webViewClient = getLolipopWebViewClient()
        } else {
            authWebView.webViewClient = getPrelolipopWebViewClient()
        }
    }


    private fun getPrelolipopWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                if (!url.contains(appConfig.redirectUrl)){
                    return true
                }

                val parsedUri = Uri.parse(url)
                val code = parsedUri.getQueryParameter("code")
                state = parsedUri.getQueryParameter("state")

                code?.let {
                    runBlocking {
                        withContext(Dispatchers.Default) {
                            requestToken(code)
                        }
                    }
                }
                return false
            }
        }
    }

    private fun getLolipopWebViewClient(): WebViewClient {
        return object : WebViewClient() {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                if (!request.url.toString().contains(appConfig.redirectUrl)){
                    return true
                }

                val code = request.url?.getQueryParameter("code")
                state = request.url?.getQueryParameter("state")

                code?.let {
                    runBlocking {
                        withContext(Dispatchers.Default) {
                            requestToken(code)
                        }
                    }
                }

                return false
            }
        }
    }

    private fun requestToken(code: String) {
        if (state == null) {
            state = ""
        }

        try {
            val accessToken = authApi.getToken(appConfig, code, state!!)
            sessionManager.saveToken(accessToken)
            val resultIntent = Intent()
            Log.d(LinkedInAuthActivity::class.java.simpleName, "Token value = ${accessToken.accessTokenValue}")
            resultIntent.putExtra("access_token", accessToken.accessTokenValue)
            setResult(RESULT_OK, resultIntent)
            finish()
        } catch (e: JSONException) {
            e.printStackTrace()
            setResult(Activity.RESULT_CANCELED)
            finish()
            Log.e(LinkedInAuthActivity::class.java.simpleName, "Error parsing token")
        }
    }


    private fun handleExtras(bundle: Bundle?) {
        bundle?.let {
            appConfig = it.getParcelable("appConfig")
            scope = it.getString("scope")
        }
    }
}