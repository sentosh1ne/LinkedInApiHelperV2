package com.sentosh1ne.linkedinapihelperv2.data.base

import org.json.JSONObject

internal class ResponseBeautifier {
    fun mapLiteProfile(initial: JSONObject?): JSONObject? {
        initial?.let {
            val mapper = ProfileMapper(it)

            return mapper.mapFirstName()
                    .mapLastName()
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