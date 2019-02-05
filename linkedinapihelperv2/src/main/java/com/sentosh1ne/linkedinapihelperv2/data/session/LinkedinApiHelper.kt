package com.sentosh1ne.linkedinapihelperv2.data.session

import android.app.Activity
import com.sentosh1ne.linkedinapihelperv2.data.api.MyProfileApi
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import org.json.JSONObject

class LinkedinApiHelper(activity: Activity) {

    private var accessToken: AccessToken? = null

    private val sessionManager: SessionManager = SessionManager(activity)

    private val myProfileApi: MyProfileApi = MyProfileApi()

    init {
        accessToken = sessionManager.getToken()
    }

    fun getUserProfileRaw(vararg fields: String): JSONObject? {
        if (accessToken != null && sessionManager.isSessionValid()) {
            return myProfileApi.getUserProfile(
                    fields = *fields,
                    token = accessToken!!.accessTokenValue
            )
        }

        return null
    }

    fun getUserProfileRaw(): JSONObject {
        if (accessToken != null && sessionManager.isSessionValid()) {
        }

        return JSONObject()
    }


    fun getUserProfilePretty(vararg fields: String): JSONObject {
        return JSONObject()
    }

    fun getUserEmail(): JSONObject {
        return JSONObject()
    }

    fun login(scope: PermissionsScope, appConfig: AppConfig) {
        sessionManager.login(scope, appConfig)
    }
}


