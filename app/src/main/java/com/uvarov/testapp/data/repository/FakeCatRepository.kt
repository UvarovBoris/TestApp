package com.uvarov.testapp.data.repository

import com.uvarov.testapp.domain.model.Breed
import com.uvarov.testapp.domain.model.Cat
import com.uvarov.testapp.domain.repository.CatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FakeCatRepository @Inject constructor() : CatRepository {

    private val _catsFlow = MutableStateFlow<List<Cat>>(emptyList())
    private var page = 0

    override fun getCats(): Flow<List<Cat>> = _catsFlow.asStateFlow()
        .onStart {
            if (_catsFlow.value.isEmpty()) {
                refreshCats()
            }
        }

    override fun getCatById(catId: String): Flow<Cat?> =
        _catsFlow.map { list -> list.firstOrNull { it.id == catId } }

    override suspend fun refreshCats() {
        delay(500)
        page = 0
        _catsFlow.value = fakeCats(0)
    }

    override suspend fun loadNextPage(): Boolean {
        delay(500)
        page++
        val nextCats = fakeCats(page)
        _catsFlow.value = _catsFlow.value + nextCats
        return page < 3
    }

    private fun fakeCats(p: Int): List<Cat> = (1..5).map { index ->
        val id = "${p}_$index"
        Cat(id = id, name = "Cat $id", imageUrl = "", breeds = fakeBreeds())
    }

    private fun fakeBreeds(): List<Breed> = listOf(
        Breed(
            id = "abys",
            name = "Abyssinian",
            temperament = "Active, Energetic, Independent",
            origin = "Egypt",
            lifeSpan = "14-17",
            description = null
        )
    )
}
