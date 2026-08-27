package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCats(): Flow<List<Cat>>
    suspend fun refreshCats()
}
