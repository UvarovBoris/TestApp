package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.local.CatLocalDataSource
import com.uvarov.testapp.data.mapper.toCat
import com.uvarov.testapp.data.mapper.toEntity
import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.data.remote.CatRemoteDataSource
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource,
    private val localDataSource: CatLocalDataSource
) : CatRepository {

    override suspend fun getCats(): List<Cat> {
        val cached = localDataSource.getCats()
        if (cached.isNotEmpty()) {
            return cached.map { it.toCat() }
        }

        return refreshCats()
    }

    override suspend fun refreshCats(): List<Cat> {
        val remote = remoteDataSource.getCats()
        localDataSource.saveCats(remote.map { it.toEntity() })
        return remote
    }
}
