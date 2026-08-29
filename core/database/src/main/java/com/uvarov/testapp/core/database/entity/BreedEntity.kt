package com.uvarov.testapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val temperament: String?,
    val origin: String?,
    val lifeSpan: String?,
    val description: String?
)
