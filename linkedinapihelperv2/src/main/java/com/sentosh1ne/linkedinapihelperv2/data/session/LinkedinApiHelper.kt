package com.sentosh1ne.linkedinapihelperv2.data.session

import android.content.Context
import org.json.JSONObject

class LinkedinApiHelper(context: Context) {

    private var accessToken: AccessToken? = null

    private var sessionManager: SessionManager = SessionManager(context)

    init {
        accessToken = sessionManager.getToken()
    }

    fun getUserProfileRaw(vararg fields: String): JSONObject {
        if (accessToken != null && sessionManager.isSessionValid()) {
            //todo make request
        }
    }

    fun getUserProfileRaw(): JSONObject {
        if (accessToken != null && sessionManager.isSessionValid()) {
            //todo make request
        }
    }


    fun getUserProfilePretty(vararg fields: String): JSONObject {
        return JSONObject()
    }

    fun getUserEmail(): JSONObject {

    }
}


