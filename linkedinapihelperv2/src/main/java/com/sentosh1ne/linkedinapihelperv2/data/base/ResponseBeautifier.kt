package com.sentosh1ne.linkedinapihelperv2.data.base

import org.json.JSONArray
import org.json.JSONObject

internal class ResponseBeautifier {
    fun mapLiteProfile(initial: JSONObject?): JSONObject? {
        initial?.let {
            val mapper = ProfileMapper(it)

            return mapper.mapFirstName()
                    .mapLastName()
                    .mapProfilePicture()
                    .get()
        }

        return null
    }
}

internal class ProfileMapper(val input: JSONObject?) {
    private var outputJson: JSONObject? = null

    fun mapFirstName(): ProfileMapper {
        return mapLocalizedString("firstName")
    }

    fun mapLastName(): ProfileMapper {
        return mapLocalizedString("lastName")
    }

    fun mapProfilePicture(): ProfileMapper {
        outputJson?.put("pictures", JSONArray())

        val picturesArray = input?.getJSONObject("profilePicture")
                ?.getJSONObject("displayImage~")
                ?.getJSONArray("elements") ?: return this

        for (i in 0..(picturesArray.length() - 1)) {
            val pictureJson = JSONObject()
            val item = picturesArray.getJSONObject(0)
            val width = item.getJSONObject("displaySize")
                    .getInt("width")
            val height = item.getJSONObject("displaySize")
                    .getInt("height")

            pictureJson.put("width", width)
            pictureJson.put("height", height)

            val identifiers = item.getJSONArray("identifiers")

            if (identifiers != null) {
                val url = identifiers.getJSONObject(0)
                pictureJson.put("url", url)
            } else {
                pictureJson.put("url", "")
            }

            outputJson?.getJSONArray("pictures")?.put(pictureJson)
        }

        return this
    }

    private fun mapLocalizedString(key: String): ProfileMapper {
        val lastName = input?.getJSONObject(key)
                ?.getJSONObject("localized")

        if (lastName != null) {
            val keys = lastName.keys()
            for (k: String in keys) {
                val newValue = lastName.getString(k)
                outputJson?.put(key, newValue)
                break
            }
        }

        return this
    }

    fun get(): JSONObject? {
        return outputJson
    }
}