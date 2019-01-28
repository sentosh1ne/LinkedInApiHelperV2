package com.sentosh1ne.linkedinapihelperv2.data.api

import com.sentosh1ne.linkedinapihelperv2.data.session.AppConfig
import okhttp3.HttpUrl

class AuthApi {
    fun buildCodeRequestUrl(scope: String, appConfig: AppConfig, state: String = ""): String {
        val url = HttpUrl.parse("https://www.linkedin.com/oauth/v2/authorization")?.newBuilder()

        url?.let {
            url.addQueryParameter("response_type", "code")
            url.addQueryParameter("client_id", appConfig.clientId)
            url.addQueryParameter("client_id", appConfig.clientId)
            url.addQueryParameter("client_id", appConfig.clientId)
            url.addQueryParameter("scope", scope)

            if (!state.isEmpty()) {
                url.addQueryParameter("state", state)
            }

            url.addQueryParameter("redirect_uri", appConfig.redirectUrl)
        }

        return url?.build().toString()
    }
}