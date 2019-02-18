package com.sentosh1ne.linkedinapihelperv2.entities

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

internal class AccessToken(var accessTokenValue: String, var expiresOn: Long) : Serializable {

    //todo
    fun isExpired(): Boolean {
        return System.currentTimeMillis() > expiresOn
    }

    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put(ACCESS_TOKEN, accessTokenValue)
        jsonObject.put(EXPIRES_ON, expiresOn)
        return jsonObject.toString()
    }

    companion object {
        val ACCESS_TOKEN = "access_token"
        val EXPIRES_ON = "expires_on"

        @Throws(JSONException::class)
        fun fromJson(json: String?): AccessToken? {
            return try {
                val jsonObject = JSONObject(json)
                AccessToken(
                        jsonObject.getString(ACCESS_TOKEN),
                        jsonObject.getLong(EXPIRES_ON)
                )
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            } catch (e: NullPointerException) {
                e.printStackTrace()
                null
            }
        }
    }
}
