package com.sentosh1ne.linkedinapihelperv2.data.session

import android.app.Activity
import com.sentosh1ne.linkedinapihelperv2.data.api.MyProfileApi
import com.sentosh1ne.linkedinapihelperv2.data.api.common.ResponseBeautifier
import com.sentosh1ne.linkedinapihelperv2.entities.AppConfig
import com.sentosh1ne.linkedinapihelperv2.entities.PermissionsScope
import org.json.JSONObject

/**
 * Entry point to the LinkedIn Api and authentication
 *
 * @param activity
 */
class LinkedinApiHelper(activity: Activity) {

    private val sessionManager: SessionManager = SessionManager(activity)

    private val myProfileApi: MyProfileApi = MyProfileApi()

    private val mapper = ResponseBeautifier()

    companion object {
        /**
         * Request code to listen for in the onActivityResult
         */
        const val ACTIVITY_REQUEST_CODE: Int = 532
    }

    /**
     * Get lite profile info "as is"
     * @param fields List of needed fields in response
     */
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


    /**
     * Get lite profile info without redundant nested json fields
     * @param fields List of needed fields in response
     */
    fun getUserProfilePretty(vararg fields: String): JSONObject? {
        return mapper.beautifyLiteProfile(getUserProfileRaw(*fields))
    }


    /**
     * Get user email "as is"
     */
    fun getUserEmailRaw(): JSONObject? {
        if (sessionManager.isSessionValid()) {
            val token = sessionManager.getToken()
            token?.let {
                return myProfileApi.getUserEmail(token.accessTokenValue)
            }
        }

        return null
    }

    /**
     * Get user email without redundant nested json fields
     */
    fun getUserEmailPretty(): String? {
        return mapper.beautifyEmail(getUserEmailRaw())
    }

    /**
     * Launch Linkedin Login flow
     * Result of login can be obtained by overriding OnActivityResult.
     *
     * Request code for LinkedIn Login is ACTIVITY_REQUEST_CODE = 532
     * @param scope Permission scope required by the application
     * @param appConfig Required auth details of the application registered in LinkedIn Developers
     */
    fun login(scope: PermissionsScope, appConfig: AppConfig) {
        sessionManager.login(scope, appConfig)
    }

    /**
     * Get current session token
     */
    fun getCurrentToken(): String? {
        return sessionManager.getToken()?.accessTokenValue
    }
}


