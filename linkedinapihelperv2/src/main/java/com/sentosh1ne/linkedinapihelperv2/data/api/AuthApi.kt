package com.sentosh1ne.linkedinapihelperv2.data.api

import android.util.Log
import com.sentosh1ne.linkedinapihelperv2.data.base.ClientProvider
import com.sentosh1ne.linkedinapihelperv2.data.session.AccessToken
import com.sentosh1ne.linkedinapihelperv2.data.session.AppConfig
import com.sentosh1ne.linkedinapihelperv2.utils.AUTH_BASE_URL
import okhttp3.HttpUrl
import org.json.JSONException
import org.json.JSONObject

internal class AuthApi {
    private val requestCreator = RequestCreator()

    /**
     * Creates url used in webview to acquire code used for authorization
     * @see com.sentosh1ne.linkedinapihelperv2.ui.LinkedInAuthActivity
     */
    fun buildCodeRequestUrl(scope: String, appConfig: AppConfig, state: String? = ""): String {
        val url = HttpUrl.parse(AUTH_BASE_URL + "authorization")?.newBuilder()

        url?.let {
            url.addQueryParameter("response_type", "code")
            url.addQueryParameter("client_id", appConfig.clientId)
            url.addQueryParameter("client_secret", appConfig.clientSecret)
            url.addQueryParameter("redirect_uri", appConfig.redirectUrl)
            url.addEncodedQueryParameter("scope", scope)

            if (state != null && !state.isEmpty()) {
                url.addQueryParameter("state", state)
            }
        }

        Log.d(AuthApi::class.java.simpleName, "Code request url = ${url?.build().toString()}")
        return url?.build().toString()
    }

    /**
     * Perform token aquiring request using code
     * @param appConfig
     * @param code
     * @param state
     * @see com.sentosh1ne.linkedinapihelperv2.ui.LinkedInAuthActivity
     */
    @Throws(JSONException::class)
    fun getToken(appConfig: AppConfig, code: String, state: String? = ""): AccessToken {
        val url = AUTH_BASE_URL + "accessToken"
        val query = HashMap<String, String>()

        query["grant_type"] = "authorization_code"
        query["code"] = code
        query["redirect_uri"] = appConfig.redirectUrl
        query["client_id"] = appConfig.clientId
        query["client_secret"] = appConfig.clientSecret

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