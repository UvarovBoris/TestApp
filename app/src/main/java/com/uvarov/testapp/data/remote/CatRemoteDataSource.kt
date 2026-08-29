package com.uvarov.testapp.data.remote

import com.uvarov.testapp.BuildConfig
import com.uvarov.testapp.data.mapper.toCat
import com.uvarov.testapp.domain.model.Cat
import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val apiService: CatApiService,
) {
    suspend fun getCats(page: Int = 0, limit: Int = 25): List<Cat> =
        apiService.getCatImages(
            page = page,
            limit = limit,
            hasBreeds = true,
            order = "ASC",
            apiKey = BuildConfig.CAT_API_KEY
        ).map { it.toCat() }
}
