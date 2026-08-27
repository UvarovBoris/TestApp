package com.uvarov.testapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: String,
    val name: String,
    val temperament: String?,
    val origin: String?,
    val lifeSpan: String?,
    val description: String?
)
