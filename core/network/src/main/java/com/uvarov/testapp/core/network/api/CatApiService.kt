package com.uvarov.testapp.core.network.api

import com.uvarov.testapp.core.network.dto.CatImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = 25,
        @Query("has_breeds") hasBreeds: Boolean = true,
        @Query("order") order: String = "ASC"
    ): List<CatImageDto>
}
