package com.uvarov.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Transaction
    @Query("SELECT * FROM cats")
    fun getCatsWithBreeds(): Flow<List<CatWithBreeds>>

    @Query("SELECT (SELECT COUNT(*) FROM cats) == 0")
    suspend fun isEmpty(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBreeds(breeds: List<BreedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCats(cats: List<CatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCatBreedCrossRefs(crossRefs: List<CatBreedCrossRef>)

    @Query("DELETE FROM cat_breed_cross_ref")
    suspend fun clearCatBreedCrossRefs()

    @Query("DELETE FROM cats")
    suspend fun clearCats()

    @Query("DELETE FROM breeds")
    suspend fun clearBreeds()
}
