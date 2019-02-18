package com.sentosh1ne.linkedinapihelperv2.data.api

import com.sentosh1ne.linkedinapihelperv2.entities.Fields
import com.sentosh1ne.linkedinapihelperv2.utils.API_BASE_URL
import org.json.JSONObject

internal class MyProfileApi {
    private val requestCreator = RequestCreator()

    private val client = ClientProvider.getClient()

    fun getUserProfile(vararg fields: String, token: String): JSONObject {
        var url = "$API_BASE_URL/me?projection=("

        fields.forEachIndexed { index, field ->
            if (index == fields.size - 1) {
                url += "$field)"
            } else {
                url += "$field,"
            }
        }

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = requestCreator.buildGetRequest(url = url, headers = headers)

        return JSONObject(
                client. newCall (request)
                        .execute()
                        .body()?.string()
        )
    }

    fun getUserEmail(token: String): JSONObject {
        var url = "$API_BASE_URL/${Fields.EmailAddress.EMAIL}"

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = requestCreator.buildGetRequest(url = url,
                headers = headers
        )

        return JSONObject(
                client.newCall(request)
                        .execute()
                        .body()?.string()
        )
    }
}