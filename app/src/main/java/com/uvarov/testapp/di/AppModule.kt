package com.uvarov.testapp.di

import com.uvarov.testapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("cat_api_key")
    fun provideCatApiKey(): String = BuildConfig.CAT_API_KEY
}
