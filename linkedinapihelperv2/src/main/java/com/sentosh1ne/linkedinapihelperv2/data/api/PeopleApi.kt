package com.sentosh1ne.linkedinapihelperv2.data.api

import com.sentosh1ne.linkedinapihelperv2.data.api.common.BaseApi
import com.sentosh1ne.linkedinapihelperv2.utils.API_BASE_URL
import com.sentosh1ne.linkedinapihelperv2.utils.concatFields
import com.sentosh1ne.linkedinapihelperv2.utils.concatPeopleIds
import org.json.JSONObject

internal class PeopleApi : BaseApi() {
    fun getProfile(profileId: String, vararg fields: String, token: String): JSONObject? {
        var url = "$API_BASE_URL/people/(id:{$profileId})?projection=("
        url = url.concatFields(*fields)

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"
        val request = requestCreator.buildGetRequest(url = url, headers = headers)
        return JSONObject(
                client.newCall(request).execute()
                        .body()
                        ?.string()
        )
    }

    fun getProfiles(ids: List<String>, vararg fields: String, token: String): JSONObject? {
        var url = "$API_BASE_URL/people/ids=List("
        url = url.concatPeopleIds(ids)
        url += "?projection=("
        url = url.concatFields(*fields)

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"
        val request = requestCreator.buildGetRequest(url = url, headers = headers)
        return JSONObject(
                client.newCall(request).execute()
                        .body()
                        ?.string()
        )
    }
}