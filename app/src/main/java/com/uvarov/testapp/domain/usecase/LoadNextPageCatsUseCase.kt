package com.uvarov.testapp.domain.usecase

import com.uvarov.testapp.data.repository.CatRepository
import javax.inject.Inject

class LoadNextPageCatsUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke(): Boolean = repository.loadNextPage()
}
