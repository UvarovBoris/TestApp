package com.uvarov.testapp.data.local

import androidx.room.withTransaction
import com.uvarov.testapp.data.mapper.toCrossRefs
import com.uvarov.testapp.data.mapper.toEntity
import com.uvarov.testapp.data.model.Cat
import javax.inject.Inject

class CatLocalDataSource @Inject constructor(
    private val catDao: CatDao,
    private val database: CatDatabase
) {
    suspend fun getCats(): List<CatWithBreeds> = catDao.getCatsWithBreeds()

    suspend fun saveCats(cats: List<Cat>) {
        database.withTransaction {
            catDao.clearCatBreedCrossRefs()
            catDao.clearCats()
            catDao.clearBreeds()
            catDao.saveBreeds(cats.flatMap { cat -> cat.breeds.map { it.toEntity() } })
            catDao.saveCats(cats.map { it.toEntity() })
            catDao.saveCatBreedCrossRefs(cats.flatMap { it.toCrossRefs() })
        }
    }
}
