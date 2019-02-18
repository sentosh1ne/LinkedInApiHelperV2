package com.sentosh1ne.linkedinapihelperv2.data.api.common

import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody

internal class RequestCreator {
    fun buildGetRequest(url: String,
                        query: Map<String, String> = HashMap(),
                        headers: Map<String, String> = HashMap()): Request {
        val request = buildRequest(url, query, headers)
        request.get()
        return request.build()
    }

    fun buildPostRequest(url: String,
                         query: Map<String, String> = HashMap(),
                         body: String = "",
                         headers: Map<String, String> = HashMap()): Request {

        val request = buildRequest(url, query, headers)
        val requestBody = RequestBody.create(MediaType.get("application/x-www-form-urlencoded"), body)
        request.post(requestBody)
        return request.build()
    }

    fun buildPutRequest(url: String,
                        query: Map<String, String> = HashMap(),
                        body: String = "",
                        headers: Map<String, String> = HashMap()): Request {
        val request = buildRequest(url, query, headers)
        val requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), body)
        request.put(requestBody)
        return request.build()
    }

    private fun buildRequest(url: String,
                             query: Map<String, String> = HashMap(),
                             headers: Map<String, String> = HashMap()): Request.Builder {

        val httpBuilder = HttpUrl.parse(url)?.newBuilder()

        query.forEach { key, value ->
            httpBuilder?.addQueryParameter(key, value)
        }

        val request = Request.Builder().url(httpBuilder?.build())

        headers.forEach { key, value ->
            request.addHeader(key, value)
        }

        return request
    }
}