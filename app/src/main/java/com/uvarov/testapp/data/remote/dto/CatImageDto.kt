package com.uvarov.testapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatImageDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("created_at")
    val createdAt: String?
)
