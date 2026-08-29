package com.uvarov.testapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uvarov.testapp.core.database.dao.CatDao
import com.uvarov.testapp.core.database.dao.FavoriteCatDao
import com.uvarov.testapp.core.database.entity.BreedEntity
import com.uvarov.testapp.core.database.entity.CatBreedCrossRef
import com.uvarov.testapp.core.database.entity.CatEntity
import com.uvarov.testapp.core.database.entity.FavoriteCatEntity

@Database(
    entities = [CatEntity::class, BreedEntity::class, CatBreedCrossRef::class, FavoriteCatEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun favoriteCatDao(): FavoriteCatDao
}
