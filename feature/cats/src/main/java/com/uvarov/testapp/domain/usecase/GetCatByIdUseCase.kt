package com.uvarov.testapp.domain.usecase

import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatByIdUseCase @Inject constructor(
    private val repository: CatRepository
) {
    operator fun invoke(catId: String): Flow<Cat?> = repository.getCatById(catId)
}
