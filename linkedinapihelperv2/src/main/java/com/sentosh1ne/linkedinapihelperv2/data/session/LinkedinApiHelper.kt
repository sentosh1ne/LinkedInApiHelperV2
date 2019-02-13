package com.sentosh1ne.linkedinapihelperv2.data.session

import android.app.Activity
import com.sentosh1ne.linkedinapihelperv2.data.api.MyProfileApi
import com.sentosh1ne.linkedinapihelperv2.data.base.ResponseBeautifier
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import org.json.JSONObject

class LinkedinApiHelper(activity: Activity) {


    private val sessionManager: SessionManager = SessionManager(activity)

    private val myProfileApi: MyProfileApi = MyProfileApi()

    private val mapper = ResponseBeautifier()

    companion object {
        const val ACTIVITY_REQUEST_CODE: Int = 532
    }

    fun getUserProfileRaw(vararg fields: String): JSONObject? {
        val token = sessionManager.getToken()
        if (token != null && sessionManager.isSessionValid()) {
            return myProfileApi.getUserProfile(
                    fields = *fields,
                    token = token.accessTokenValue
            )
        }

        return null
    }

    fun getUserProfileRaw(): JSONObject {
        //todo use all fields
        val token = sessionManager.getToken()
        if (token != null && sessionManager.isSessionValid()) {
        }

        return JSONObject()
    }


    fun getUserProfilePretty(vararg fields: String): JSONObject? {
        return mapper.beautifyLiteProfile(getUserProfileRaw())
    }


    fun getUserEmailRaw(): JSONObject? {
        if (sessionManager.isSessionValid()) {
            val token = sessionManager.getToken()
            token?.let {
                return myProfileApi.getUserEmail(token.accessTokenValue)
            }
        }

        return null
    }

    fun getUserEmailPretty(): String? {
        return mapper.beautifyEmail(getUserEmailRaw())
    }

    fun login(scope: PermissionsScope, appConfig: AppConfig) {
        sessionManager.login(scope, appConfig)
    }
}


