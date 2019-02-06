package com.sentosh1ne.linkedinapihelperv2.data.api

import com.sentosh1ne.linkedinapihelperv2.data.base.ClientProvider
import org.json.JSONObject

internal class MyProfileApi {
    val baseUrl = " https://api.linkedin.com/v2"

    val requestCreator = RequestCreator()

    fun getUserProfile(vararg fields: String, token: String): JSONObject {
        var url = "$baseUrl/me?projection=("

        fields.forEachIndexed { index, field ->
            if (index == fields.size - 1) {
                url += "$field)"
            } else {
                url += "$field,"
            }
        }

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = requestCreator.buildGetRequest(url = url,
                headers = headers
        )

        return JSONObject(
                ClientProvider.getClient()
                        .newCall(request)
                        .execute()
                        .body()?.string()
        )
    }
}