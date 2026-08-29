package com.uvarov.testapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.uvarov.testapp.core.database.CatDatabase
import com.uvarov.testapp.core.database.dao.CatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCatDatabase(@ApplicationContext context: Context): CatDatabase =
        Room.databaseBuilder(
            context,
            CatDatabase::class.java,
            "cats.db"
        ).build()

    @Provides
    fun provideCatDao(database: CatDatabase): CatDao = database.catDao()
}
