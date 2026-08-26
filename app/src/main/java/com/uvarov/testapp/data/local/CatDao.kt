package com.uvarov.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatDao {

    @Query("SELECT * FROM cats")
    suspend fun getCats(): List<CatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCats(cats: List<CatEntity>)

    @Query("DELETE FROM cats")
    suspend fun clearCats()
}
