package com.uvarov.testapp.core.data.datasource

import androidx.room.withTransaction
import com.uvarov.testapp.core.data.mapper.toCrossRefs
import com.uvarov.testapp.core.data.mapper.toEntity
import com.uvarov.testapp.core.database.CatDatabase
import com.uvarov.testapp.core.database.dao.CatDao
import com.uvarov.testapp.core.database.entity.CatWithBreeds
import com.uvarov.testapp.domain.model.Cat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatLocalDataSource @Inject constructor(
    private val catDao: CatDao,
    private val database: CatDatabase
) {
    fun getCats(): Flow<List<CatWithBreeds>> = catDao.getCatsWithBreeds()

    suspend fun isEmpty(): Boolean = catDao.isEmpty()

    suspend fun getCount(): Int = catDao.getCount()

    suspend fun saveCats(cats: List<Cat>) {
        database.withTransaction {
            catDao.clearCatBreedCrossRefs()
            catDao.clearCats()
            catDao.clearBreeds()
            insertCatsInternal(cats)
        }
    }

    suspend fun appendCats(cats: List<Cat>) {
        database.withTransaction {
            insertCatsInternal(cats)
        }
    }

    private suspend fun insertCatsInternal(cats: List<Cat>) {
        catDao.saveBreeds(cats.flatMap { cat -> cat.breeds.map { it.toEntity() } })
        catDao.saveCats(cats.map { it.toEntity() })
        catDao.saveCatBreedCrossRefs(cats.flatMap { it.toCrossRefs() })
    }
}
