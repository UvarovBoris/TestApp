package com.uvarov.testapp.domain.repository

import com.uvarov.testapp.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCats(): Flow<List<Cat>>
    fun getCatById(catId: String): Flow<Cat?>
    suspend fun refreshCats()
    suspend fun loadNextPage(): Boolean
}
