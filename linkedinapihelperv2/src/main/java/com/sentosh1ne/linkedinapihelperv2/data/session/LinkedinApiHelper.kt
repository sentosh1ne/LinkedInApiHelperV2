package com.sentosh1ne.linkedinapihelperv2.data.session

import android.content.Context
import com.sentosh1ne.linkedinapihelperv2.data.api.MyProfileApi
import org.json.JSONObject

class LinkedinApiHelper(context: Context) {

    private var accessToken: AccessToken? = null

    private val sessionManager: SessionManager = SessionManager(context)

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
}


