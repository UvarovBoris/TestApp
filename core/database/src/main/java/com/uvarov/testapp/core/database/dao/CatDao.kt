package com.uvarov.testapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.uvarov.testapp.core.database.entity.BreedEntity
import com.uvarov.testapp.core.database.entity.CatBreedCrossRef
import com.uvarov.testapp.core.database.entity.CatEntity
import com.uvarov.testapp.core.database.entity.CatWithBreeds
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Transaction
    @Query("SELECT * FROM cats")
    fun getCatsWithBreeds(): Flow<List<CatWithBreeds>>

    @Query("SELECT (SELECT COUNT(*) FROM cats) == 0")
    suspend fun isEmpty(): Boolean

    @Query("SELECT COUNT(*) FROM cats")
    suspend fun getCount(): Int

    @Upsert
    suspend fun saveBreeds(breeds: List<BreedEntity>)

    @Upsert
    suspend fun saveCats(cats: List<CatEntity>)

    @Upsert
    suspend fun saveCatBreedCrossRefs(crossRefs: List<CatBreedCrossRef>)

    @Query("DELETE FROM cat_breed_cross_ref")
    suspend fun clearCatBreedCrossRefs()

    @Query("DELETE FROM cats")
    suspend fun clearCats()

    @Query("DELETE FROM breeds")
    suspend fun clearBreeds()
}
