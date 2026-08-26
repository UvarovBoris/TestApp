package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.data.remote.CatRemoteDataSource
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource
) : CatRepository {

    override suspend fun getCats(): List<Cat> = remoteDataSource.getCats()
}
