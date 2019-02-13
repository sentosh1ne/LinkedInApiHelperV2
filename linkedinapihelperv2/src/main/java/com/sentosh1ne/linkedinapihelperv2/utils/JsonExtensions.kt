package com.sentosh1ne.linkedinapihelperv2.utils

import org.json.JSONArray

fun JSONArray?.isEmpty(): Boolean {
    if (this == null) {
        return true
    }

    if (length() == 0) {
        return true
    }

    return false
}