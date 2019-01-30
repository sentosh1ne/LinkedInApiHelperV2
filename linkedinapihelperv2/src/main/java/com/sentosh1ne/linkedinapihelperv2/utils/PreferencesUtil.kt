package com.sentosh1ne.linkedinapihelperv2.utils

import android.content.Context
import android.content.SharedPreferences

internal class PreferencesUtil private constructor(context: Context) {
    private var sharedPreferences: SharedPreferences

    companion object {
        private const val SHARED_PREFERENCES_NAME = "linkedinapihelper_preferences"

        private lateinit var preference: PreferencesUtil

        fun getInstance(context: Context): PreferencesUtil {

            if (!::preference.isInitialized) {
                preference = PreferencesUtil(context)
            }

            return preference
        }

        const val ACCESS_TOKEN = "access_token"
        val AUTH_CODE = "auth_code"

    }

    init {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }

    fun clearPrefernces() {
        sharedPreferences.edit().clear().apply()
    }
}