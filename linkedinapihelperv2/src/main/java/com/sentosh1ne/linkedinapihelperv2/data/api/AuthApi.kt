package com.sentosh1ne.linkedinapihelperv2.data.api

import com.sentosh1ne.linkedinapihelperv2.data.base.ClientProvider
import com.sentosh1ne.linkedinapihelperv2.data.session.AccessToken
import com.sentosh1ne.linkedinapihelperv2.data.session.AppConfig
import okhttp3.HttpUrl
import org.json.JSONException
import org.json.JSONObject

internal class AuthApi {
    private val baseUrl = "https://www.linkedin.com/oauth/v2/"
    private val requestCreator = RequestCreator()

    /**
     * Creates url used in webview to acquire code used for authorization
     * @see com.sentosh1ne.linkedinapihelperv2.ui.LinkedInAuthActivity
     */
    fun buildCodeRequestUrl(scope: String, appConfig: AppConfig, state: String? = ""): String {
        val url = HttpUrl.parse(baseUrl + "authorization")?.newBuilder()

        url?.let {
            url.addQueryParameter("response_type", "code")
            url.addQueryParameter("client_id", appConfig.clientId)
            url.addQueryParameter("client_secret", appConfig.clientSecret)
            url.addQueryParameter("redirect_uri", appConfig.redirectUrl)
            url.addQueryParameter("scope", scope)

            if (state != null && !state.isEmpty()) {
                url.addQueryParameter("state", state)
            }

            url.addQueryParameter("redirect_uri", appConfig.redirectUrl)
        }

        return url?.build().toString()
    }

    @Throws(JSONException::class)
    fun getToken(appConfig: AppConfig, code: String, state: String? = ""): AccessToken {
        val url = baseUrl + "accessToken"
        val query = HashMap<String, String>()

        query["grant_type"] = "authorization_code"
        query["code"] = code
        query["redirect_uri"] = appConfig.redirectUrl
        query["client_id"] = appConfig.clientId

        if (state != null && !state.isEmpty()) {
            query["state"] = state
        }

        val request = requestCreator.buildPostRequest(url, query)

        val response = ClientProvider.getClient().newCall(request).execute()
        val responseJson = JSONObject(response.body()?.string())

        return AccessToken(
                responseJson.getString("access_token"),
                System.currentTimeMillis() + responseJson.getLong("expires_in")
        )
    }
}