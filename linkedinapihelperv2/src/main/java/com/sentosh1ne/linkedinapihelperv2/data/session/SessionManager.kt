package com.sentosh1ne.linkedinapihelperv2.data.session

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import com.sentosh1ne.linkedinapihelperv2.ui.LinkedInAuthActivity
import com.sentosh1ne.linkedinapihelperv2.utils.PreferencesUtil

internal class SessionManager(private val activity: Activity) {

    private val preferences = PreferencesUtil.getInstance(activity)

    fun getToken(): AccessToken? {
        val tokenString = preferences?.getToken()
        return AccessToken.fromJson(tokenString)
    }

    fun saveToken(token: AccessToken) {
        if (!token.isExpired()) {
            preferences?.saveToken(token.toJson())
        } else {
            Log.e(SessionManager::class.simpleName, "Error saving expired token")
        }
    }

    fun isSessionValid(): Boolean {
        val token = getToken()
        return token != null && !token.isExpired()
    }

    fun login(scope: PermissionsScope, appConfig: AppConfig) {
        val intent = Intent(activity, LinkedInAuthActivity::class.java)
        intent.putExtra("scope", scope.scopeValue)
        intent.putExtra("appConfig", appConfig)
        activity.startActivityForResult(intent, LinkedinApiHelper.ACTIVITY_REQUEST_CODE)
    }

}
