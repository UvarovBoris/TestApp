package com.uvarov.testapp.di

import android.content.Context
import androidx.room.Room
import com.uvarov.testapp.data.local.CatDao
import com.uvarov.testapp.data.local.CatDatabase
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
