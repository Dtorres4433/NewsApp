package com.torresdavid.newsapp.Classes

import android.os.Parcel
import android.os.Parcelable

data class NewsAPI(
    var status: String,
    var totalResult: Int,
    var articles: Array<Articles>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.createTypedArray(Articles) as Array<Articles>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeInt(totalResult)
        parcel.writeTypedArray(articles, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsAPI> {
        override fun createFromParcel(parcel: Parcel): NewsAPI {
            return NewsAPI(parcel)
        }

        override fun newArray(size: Int): Array<NewsAPI?> {
            return arrayOfNulls(size)
        }
    }
}
