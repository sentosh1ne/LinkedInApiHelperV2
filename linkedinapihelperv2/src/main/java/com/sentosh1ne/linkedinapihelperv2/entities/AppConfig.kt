package com.sentosh1ne.linkedinapihelperv2.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Object, holding required information to authorize user
 * @param clientId Value from application's auth info at linkedin.com/developers/apps
 * @param clientSecret Value from application's auth info at linkedin.com/developers/apps
 * @param redirectUrl One of the added redirectUrl in auth options
 */
class AppConfig(val clientId: String, val clientSecret: String, val redirectUrl: String) : Parcelable {
    internal constructor(source: Parcel) : this(
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