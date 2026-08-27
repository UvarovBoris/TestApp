package com.uvarov.testapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BreedDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    @SerializedName("description")
    val description: String?
)
