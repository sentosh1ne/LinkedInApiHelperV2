package com.sentosh1ne.linkedinapihelperv2.data.session

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

internal class AccessToken(var accessTokenValue: String, var expiresIn: Long) : Serializable {
    var expiresOn = System.currentTimeMillis() + expiresIn
    //todo
    fun isExpired(): Boolean {
        return System.currentTimeMillis() > expiresOn
    }

    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("access_token", accessTokenValue)
        jsonObject.put("expires_in", expiresIn)
        jsonObject.put("expires_on", expiresOn)
        return jsonObject.toString()
    }

    companion object {
        @Throws(JSONException::class)
        fun fromJson(json: String?): AccessToken {
            val jsonObject = JSONObject(json)
            val accessToken = AccessToken(
                    jsonObject.getString("access_token"),
                    jsonObject.getLong("expires_in")
            )

            accessToken.expiresOn = jsonObject.getLong("expires_on")
            return accessToken
        }
    }
}
