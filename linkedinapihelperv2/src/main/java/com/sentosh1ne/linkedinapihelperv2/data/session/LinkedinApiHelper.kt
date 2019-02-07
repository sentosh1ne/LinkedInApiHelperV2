package com.sentosh1ne.linkedinapihelperv2.data.session

import android.app.Activity
import com.sentosh1ne.linkedinapihelperv2.data.api.MyProfileApi
import com.sentosh1ne.linkedinapihelperv2.data.base.ResponseBeautifier
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import org.json.JSONObject

class LinkedinApiHelper(activity: Activity) {

    private var accessToken: AccessToken? = null

    private val sessionManager: SessionManager = SessionManager(activity)

    private val myProfileApi: MyProfileApi = MyProfileApi()

    private val mapper = ResponseBeautifier()

    companion object {
        const val ACTIVITY_REQUEST_CODE: Int = 532
    }

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


    fun getUserProfilePretty(vararg fields: String): JSONObject? {
        if (accessToken != null && sessionManager.isSessionValid()) {
            val userProfile = myProfileApi.getUserProfile(
                    fields = *fields,
                    token = accessToken!!.accessTokenValue)

            return mapper.mapLiteProfile(userProfile)
        }

        return null
    }

    fun getUserEmail(): JSONObject {
        return JSONObject()
    }

    fun login(scope: PermissionsScope, appConfig: AppConfig) {
        sessionManager.login(scope, appConfig)
    }
}


