package com.cvs.tagsnap.model

import com.google.gson.annotations.SerializedName

data class SnapImage(
    val title: String,
    val link: String,
    val media: Media,
    val author: String,
    val published: String,
    val description: String,
    val dateTaken: String,
)

data class Media(
    @SerializedName("m")
    val media: String
)
