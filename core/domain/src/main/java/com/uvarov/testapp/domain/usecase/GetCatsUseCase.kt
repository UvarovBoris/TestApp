package com.uvarov.testapp.domain.usecase

import com.uvarov.testapp.domain.model.Cat
import com.uvarov.testapp.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(
    private val repository: CatRepository
) {
    operator fun invoke(): Flow<List<Cat>> = repository.getCats()
}
