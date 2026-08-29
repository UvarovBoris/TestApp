package com.uvarov.testapp.domain.model

data class Breed(
    val id: String,
    val name: String,
    val temperament: String?,
    val origin: String?,
    val lifeSpan: String?,
    val description: String?
)
