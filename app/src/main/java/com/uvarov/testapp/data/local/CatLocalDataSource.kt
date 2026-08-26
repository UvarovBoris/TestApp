package com.uvarov.testapp.data.local

import javax.inject.Inject

class CatLocalDataSource @Inject constructor(
    private val catDao: CatDao
) {
    suspend fun getCats(): List<CatEntity> = catDao.getCats()

    suspend fun saveCats(cats: List<CatEntity>) {
        catDao.clearCats()
        catDao.saveCats(cats)
    }
}
