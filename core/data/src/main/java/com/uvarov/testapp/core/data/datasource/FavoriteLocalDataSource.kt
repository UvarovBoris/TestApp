package com.uvarov.testapp.core.data.datasource

import com.uvarov.testapp.core.database.dao.FavoriteCatDao
import com.uvarov.testapp.core.database.entity.CatWithBreeds
import com.uvarov.testapp.core.database.entity.FavoriteCatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteLocalDataSource @Inject constructor(
    private val favoriteCatDao: FavoriteCatDao
) {
    fun observeFavoriteCats(): Flow<List<CatWithBreeds>> = favoriteCatDao.observeFavoriteCats()

    fun isFavorite(catId: String): Flow<Boolean> = favoriteCatDao.isFavorite(catId)

    suspend fun addFavorite(catId: String, addedAt: Long) {
        favoriteCatDao.addFavorite(FavoriteCatEntity(catId = catId, addedAt = addedAt))
    }

    suspend fun removeFavorite(catId: String) {
        favoriteCatDao.removeFavorite(catId)
    }
}
