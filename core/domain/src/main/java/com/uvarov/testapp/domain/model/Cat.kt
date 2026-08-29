package com.uvarov.testapp.domain.model

data class Cat(
    val id: String,
    val name: String,
    val imageUrl: String,
    val breeds: List<Breed> = emptyList()
)
