package com.sentosh1ne.linkedinapihelperv2.data.session

import android.content.Context
import com.sentosh1ne.linkedinapihelperv2.utils.PreferencesUtil

internal class SessionManager(val context: Context) {
    var accessToken: AccessToken? = null
    val preferences = PreferencesUtil.getInstance(context)

    fun saveToken(token: AccessToken) {
        preferences.saveToken(token.toJson())
    }

    fun setToken(token: AccessToken) {
        this.accessToken = token
        saveToken(token)
    }

    fun getToken(): AccessToken {
        val tokenString = preferences.getToken()
        return AccessToken.fromJson(tokenString)
    }

    fun recover() {

    }

    fun isSessionValid(): Boolean {
        val token = getToken()
        return token != null && token.isExpired()
    }
}