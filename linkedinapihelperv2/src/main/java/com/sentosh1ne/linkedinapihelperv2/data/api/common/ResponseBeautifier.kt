package com.sentosh1ne.linkedinapihelperv2.data.api.common

import com.sentosh1ne.linkedinapihelperv2.utils.isEmpty
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

internal class ResponseBeautifier {
    fun beautifyLiteProfile(initial: JSONObject?): JSONObject? {
        initial?.let {
            val mapper = ProfileMapper(it)
            return mapper.mapFirstName()
                    .mapLastName()
                    .mapProfilePicture()
                    .get()
        }

        return null
    }

    fun beautifyEmail(initial: JSONObject?): String? {
        initial?.let {

            val elements = initial.getJSONArray("elements")

            if (!elements.isEmpty()) {
                return elements.getJSONObject(0)
                        .getJSONObject("handle~")
                        .getString("emailAddress")
            }

            return null
        }

        return null
    }
}

internal class ProfileMapper(private val input: JSONObject?) {
    private var outputJson: JSONObject? = JSONObject()

    fun mapFirstName(): ProfileMapper {
        return mapLocalizedString("firstName")
    }

    fun mapLastName(): ProfileMapper {
        return mapLocalizedString("lastName")
    }

    fun mapProfilePicture(): ProfileMapper {
        outputJson?.put("pictures", JSONArray())

        try {
            val picturesArray = input?.getJSONObject("profilePicture")
                    ?.getJSONObject("displayImage~")
                    ?.getJSONArray("elements") ?: return this

            for (index in 0..(picturesArray.length() - 1)) {
                val pictureJson = JSONObject()

                val item = picturesArray.getJSONObject(index)

                val displaySize = item.getJSONObject("data")
                        .getJSONObject("com.linkedin.digitalmedia.mediaartifact.StillImage")
                        .getJSONObject("displaySize")

                val width = displaySize.getInt("width")

                val height = displaySize.getInt("height")

                pictureJson.put("width", width)
                pictureJson.put("height", height)

                val identifiers = item.getJSONArray("identifiers")

                if (identifiers != null && identifiers.length() > 0) {
                    val url = identifiers.getJSONObject(0)
                            .getString("file")

                    pictureJson.put("url", url)
                } else {
                    pictureJson.put("url", "")
                }

                outputJson?.getJSONArray("pictures")?.put(pictureJson)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return this
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