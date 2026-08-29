package com.uvarov.testapp.core.network.di

import com.uvarov.testapp.core.network.BuildConfig
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request().newBuilder()
                .header("x-api-key", BuildConfig.CAT_API_KEY)
                .build()
        )
}
