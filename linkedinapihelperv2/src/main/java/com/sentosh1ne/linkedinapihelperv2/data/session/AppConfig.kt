package com.sentosh1ne.linkedinapihelperv2.data.session

import android.os.Parcel
import android.os.Parcelable

class AppConfig(val clientId: String, val clientSecret: String, val redirectUrl: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(clientId)
        writeString(clientSecret)
        writeString(redirectUrl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AppConfig> = object : Parcelable.Creator<AppConfig> {
            override fun createFromParcel(source: Parcel): AppConfig = AppConfig(source)
            override fun newArray(size: Int): Array<AppConfig?> = arrayOfNulls(size)
        }
    }
}