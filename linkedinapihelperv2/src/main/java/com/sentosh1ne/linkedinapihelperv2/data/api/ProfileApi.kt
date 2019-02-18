package com.sentosh1ne.linkedinapihelperv2.data.api

import com.sentosh1ne.linkedinapihelperv2.data.api.common.BaseApi
import com.sentosh1ne.linkedinapihelperv2.entities.Fields
import com.sentosh1ne.linkedinapihelperv2.utils.API_BASE_URL
import com.sentosh1ne.linkedinapihelperv2.utils.concatFields
import org.json.JSONObject

internal class MyProfileApi : BaseApi() {
    fun getUserProfile(vararg fields: String, token: String): JSONObject {
        var url = "$API_BASE_URL/me?projection=("
        url = url.concatFields(*fields)

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = this.requestCreator.buildGetRequest(url = url, headers = headers)

        return JSONObject(
                this.client.newCall(request)
                        .execute()
                        .body()?.string()
        )
    }

    fun getUserEmail(token: String): JSONObject {
        var url = "$API_BASE_URL/${Fields.EmailAddress.EMAIL}"

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = this.requestCreator.buildGetRequest(url = url,
                headers = headers
        )

        return JSONObject(
                client.newCall(request)
                        .execute()
                        .body()?.string()
        )
    }
}