package com.sentosh1ne.linkedinapihelperv2.data.session

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import com.sentosh1ne.linkedinapihelperv2.ui.LinkedInAuthActivity
import com.sentosh1ne.linkedinapihelperv2.utils.PreferencesUtil

internal class SessionManager(context: Context) {

    val preferences = PreferencesUtil.getInstance(context)

    fun getToken(): AccessToken? {
        val tokenString = preferences.getToken()
        return AccessToken.fromJson(tokenString)
    }

    fun saveToken(token: AccessToken) {
        if (!token.isExpired()) {
            preferences.saveToken(token.toJson())
        } else {
            Log.e(SessionManager::class.simpleName, "Error saving expired token")
        }
    }

    fun isSessionValid(): Boolean {
        val token = getToken()
        return token != null && token.isExpired()
    }

    fun login(activity: Activity, scope: PermissionsScope, appConfig: AppConfig) {
        val intent = Intent(activity, LinkedInAuthActivity::class.java)
        intent.putExtra("scope", scope.scopeValue)
        intent.putExtra("appConfig", appConfig)
        activity.startActivityForResult(intent, Companion.REQUEST_CODE)
    }

    companion object {
        private const val REQUEST_CODE = 444555
    }
}
