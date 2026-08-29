package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.local.CatLocalDataSource
import com.uvarov.testapp.data.mapper.toCat
import com.uvarov.testapp.data.remote.CatRemoteDataSource
import com.uvarov.testapp.domain.model.Cat
import com.uvarov.testapp.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource,
    private val localDataSource: CatLocalDataSource
) : CatRepository {

    private val mutex = Mutex()
    private val pageSize = 25

    override fun getCats(): Flow<List<Cat>> {
        return localDataSource.getCats()
            .map { list -> list.map { it.toCat() } }
    }

    override fun getCatById(catId: String): Flow<Cat?> {
        return localDataSource.getCats()
            .map { list -> list.firstOrNull { it.cat.id == catId }?.toCat() }
    }

    override suspend fun refreshCats() {
        mutex.withLock {
            localDataSource.saveCats(emptyList())
        }
        loadNextPage()
    }

    override suspend fun loadNextPage(): Boolean {
        return mutex.withLock {
            val count = localDataSource.getCount()
            val nextPage = count / pageSize
            val remote = remoteDataSource.getCats(page = nextPage, limit = pageSize)
            if (remote.isNotEmpty()) {
                localDataSource.appendCats(remote)
            }
            remote.size >= pageSize
        }
    }
}
