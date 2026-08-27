package com.uvarov.testapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CatEntity::class, BreedEntity::class, CatBreedCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}
