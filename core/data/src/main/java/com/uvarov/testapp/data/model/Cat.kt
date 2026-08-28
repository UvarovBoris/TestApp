package com.uvarov.testapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    val id: String,
    val name: String,
    val imageUrl: String,
    val breeds: List<Breed> = emptyList()
)
