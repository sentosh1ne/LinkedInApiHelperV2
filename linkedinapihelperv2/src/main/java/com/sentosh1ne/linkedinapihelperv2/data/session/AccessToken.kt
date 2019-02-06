package com.sentosh1ne.linkedinapihelperv2.data.session

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
        jsonObject.put("access_token", accessTokenValue)
        jsonObject.put("expires_on", expiresOn)
        return jsonObject.toString()
    }

    companion object {
        @Throws(JSONException::class)
        fun fromJson(json: String?): AccessToken? {
            return try {
                val jsonObject = JSONObject(json)
                AccessToken(
                        jsonObject.getString("access_token"),
                        jsonObject.getLong("expires_on")
                )
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            } catch (e: NullPointerException){
                e.printStackTrace()
                null
            }
        }
    }
}
