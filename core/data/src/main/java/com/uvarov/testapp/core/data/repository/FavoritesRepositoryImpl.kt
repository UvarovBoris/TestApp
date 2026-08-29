package com.uvarov.testapp.core.data.repository

import com.uvarov.testapp.core.data.datasource.FavoriteLocalDataSource
import com.uvarov.testapp.core.data.mapper.toCat
import com.uvarov.testapp.domain.model.Cat
import com.uvarov.testapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteLocalDataSource
) : FavoritesRepository {

    override fun observeFavoriteCats(): Flow<List<Cat>> =
        localDataSource.observeFavoriteCats()
            .map { list -> list.map { it.toCat() } }

    override fun isFavorite(catId: String): Flow<Boolean> =
        localDataSource.isFavorite(catId)

    override suspend fun addFavorite(catId: String) {
        localDataSource.addFavorite(catId = catId, addedAt = System.currentTimeMillis())
    }

    override suspend fun removeFavorite(catId: String) {
        localDataSource.removeFavorite(catId)
    }
}
