package com.uvarov.testapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.uvarov.testapp.core.database.entity.CatWithBreeds
import com.uvarov.testapp.core.database.entity.FavoriteCatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteCatEntity)

    @Query("DELETE FROM favorite_cats WHERE catId = :catId")
    suspend fun removeFavorite(catId: String)

    @Transaction
    @Query(
        "SELECT c.* FROM cats c " +
            "INNER JOIN favorite_cats f ON c.id = f.catId " +
            "ORDER BY f.addedAt DESC"
    )
    fun observeFavoriteCats(): Flow<List<CatWithBreeds>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cats WHERE catId = :catId)")
    fun isFavorite(catId: String): Flow<Boolean>
}
