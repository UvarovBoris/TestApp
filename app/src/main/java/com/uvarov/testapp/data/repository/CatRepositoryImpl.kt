package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.local.CatLocalDataSource
import com.uvarov.testapp.data.mapper.toCat
import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.data.remote.CatRemoteDataSource
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
    private var currentPage = -1
    private val pageSize = 25

    override fun getCats(): Flow<List<Cat>> {
        return localDataSource.getCats()
            .map { list -> list.map { it.toCat() } }
    }

    override suspend fun refreshCats() {
        mutex.withLock {
            currentPage = -1
            val remote = remoteDataSource.getCats(page = 0, limit = pageSize)
            currentPage = 0
            localDataSource.saveCats(remote)
        }
    }

    override suspend fun loadNextPage(): Boolean {
        return mutex.withLock {
            val nextPage = currentPage + 1
            val remote = remoteDataSource.getCats(page = nextPage, limit = pageSize)
            if (remote.isNotEmpty()) {
                currentPage = nextPage
                localDataSource.appendCats(remote)
            }
            remote.size >= pageSize
        }
    }
}
