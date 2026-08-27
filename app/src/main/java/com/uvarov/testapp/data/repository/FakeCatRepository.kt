package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.model.Breed
import com.uvarov.testapp.data.model.Cat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FakeCatRepository @Inject constructor() : CatRepository {

    private val _catsFlow = MutableStateFlow<List<Cat>>(emptyList())

    override fun getCats(): Flow<List<Cat>> = _catsFlow.asStateFlow()
        .onStart {
            if (_catsFlow.value.isEmpty()) {
                refreshCats()
            }
        }

    override suspend fun refreshCats() {
        delay(500)
        _catsFlow.value = fakeCats()
    }

    private fun fakeCats(): List<Cat> = listOf(
        Cat(id = "1", name = "Whiskers", imageUrl = "", breeds = fakeBreeds()),
        Cat(id = "2", name = "Milo", imageUrl = "", breeds = fakeBreeds()),
        Cat(id = "3", name = "Luna", imageUrl = "", breeds = fakeBreeds()),
        Cat(id = "4", name = "Oliver", imageUrl = "", breeds = fakeBreeds()),
        Cat(id = "5", name = "Bella", imageUrl = "", breeds = fakeBreeds())
    )

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
