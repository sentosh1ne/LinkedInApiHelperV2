package com.sentosh1ne.linkedinapihelperv2.utils

fun String.concatFields(vararg fields: String): String {
    val stringBuffer = StringBuffer(this)
    fields.forEachIndexed { index, field ->
        if (index == fields.size - 1) {
            stringBuffer.append("$field)")
        } else {
            stringBuffer.append("$field,")
        }
    }

    return stringBuffer.toString()
}

fun String.concatPeopleIds(ids: List<String>): String {
    val stringBuffer = StringBuffer(this)
    ids.forEachIndexed { index, id ->
        if (index == ids.size - 1) {
            stringBuffer.append("(id:{$id}))")
        } else {
            stringBuffer.append("(id:{$id}),")
        }
    }

    return stringBuffer.toString()
}