package com.daivers.covinapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val sourceName: String?,
    val author: String?,
    val title: String?,
    val webUrl: String,
    val imgUrl: String?,
    val datePublished: String,
    val desc: String,
    val content: String?
) : Parcelable