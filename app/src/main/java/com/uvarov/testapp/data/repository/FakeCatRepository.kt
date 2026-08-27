package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.model.Breed
import com.uvarov.testapp.data.model.Cat
import javax.inject.Inject
import kotlinx.coroutines.delay

class FakeCatRepository @Inject constructor() : CatRepository {

    override suspend fun getCats(): List<Cat> {
        delay(500)
        return fakeCats()
    }

    override suspend fun refreshCats(): List<Cat> {
        delay(500)
        return fakeCats()
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
