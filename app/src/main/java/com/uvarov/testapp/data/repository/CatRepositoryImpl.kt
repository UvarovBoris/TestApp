package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.local.CatLocalDataSource
import com.uvarov.testapp.data.mapper.toCat
import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.data.remote.CatRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource,
    private val localDataSource: CatLocalDataSource
) : CatRepository {

    override fun getCats(): Flow<List<Cat>> {
        return localDataSource.getCats()
            .map { list -> list.map { it.toCat() } }
            .onStart {
                if (localDataSource.isEmpty()) {
                    runCatching { refreshCats() }
                }
            }
    }

    override suspend fun refreshCats() {
        val remote = remoteDataSource.getCats()
        localDataSource.saveCats(remote)
    }
}
