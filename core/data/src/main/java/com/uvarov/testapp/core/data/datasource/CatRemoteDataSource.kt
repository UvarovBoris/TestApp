package com.uvarov.testapp.core.data.datasource

import com.uvarov.testapp.core.data.mapper.toCat
import com.uvarov.testapp.domain.model.Cat
import com.uvarov.testapp.core.network.api.CatApiService
import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val apiService: CatApiService
) {
    suspend fun getCats(page: Int = 0, limit: Int = 25): List<Cat> =
        apiService.getCatImages(
            page = page,
            limit = limit,
            hasBreeds = true,
            order = "ASC"
        ).map { it.toCat() }
}
