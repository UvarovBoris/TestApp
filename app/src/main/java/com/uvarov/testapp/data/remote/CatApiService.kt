package com.uvarov.testapp.data.remote

import com.uvarov.testapp.data.remote.dto.CatImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("v1/images/search")
    suspend fun getCatImages(
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String
    ): List<CatImageDto>
}
