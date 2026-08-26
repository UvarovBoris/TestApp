package com.uvarov.testapp.di

import com.uvarov.testapp.data.repository.CatRepository
import com.uvarov.testapp.data.repository.CatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCatRepository(repository: CatRepositoryImpl): CatRepository
}
