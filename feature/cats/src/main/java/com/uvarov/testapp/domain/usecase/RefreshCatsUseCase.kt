package com.uvarov.testapp.domain.usecase

import com.uvarov.testapp.domain.repository.CatRepository
import javax.inject.Inject

class RefreshCatsUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend operator fun invoke() = repository.refreshCats()
}
