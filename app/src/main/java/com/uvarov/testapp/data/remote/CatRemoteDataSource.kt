package com.uvarov.testapp.data.remote

import com.uvarov.testapp.BuildConfig
import com.uvarov.testapp.data.mapper.toCat
import com.uvarov.testapp.data.model.Cat
import javax.inject.Inject

class CatRemoteDataSource @Inject constructor(
    private val apiService: CatApiService
) {
    suspend fun getCats(limit: Int = 10): List<Cat> =
        apiService.getCatImages(limit = limit, apiKey = BuildConfig.CAT_API_KEY)
            .map { it.toCat() }
}
