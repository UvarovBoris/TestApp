package com.uvarov.testapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val imageUrl: String
)
