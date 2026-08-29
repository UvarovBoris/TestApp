package com.uvarov.testapp.domain.repository

import com.uvarov.testapp.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun observeFavoriteCats(): Flow<List<Cat>>
    fun isFavorite(catId: String): Flow<Boolean>
    suspend fun addFavorite(catId: String)
    suspend fun removeFavorite(catId: String)
}
