package com.uvarov.testapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cats")
data class FavoriteCatEntity(
    @PrimaryKey
    val catId: String,
    val addedAt: Long
)
