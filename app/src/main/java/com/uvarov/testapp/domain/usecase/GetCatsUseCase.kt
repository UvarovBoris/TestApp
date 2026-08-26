package com.uvarov.testapp.domain.usecase

import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.data.repository.CatRepository
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke(): List<Cat> = repository.getCats()
}
