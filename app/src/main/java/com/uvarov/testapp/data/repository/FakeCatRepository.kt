package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.model.Cat
import javax.inject.Inject
import kotlinx.coroutines.delay

class FakeCatRepository @Inject constructor() : CatRepository {

    override suspend fun getCats(): List<Cat> {
        delay(500)
        return listOf(
            Cat(id = "1", name = "Whiskers", imageUrl = ""),
            Cat(id = "2", name = "Milo", imageUrl = ""),
            Cat(id = "3", name = "Luna", imageUrl = ""),
            Cat(id = "4", name = "Oliver", imageUrl = ""),
            Cat(id = "5", name = "Bella", imageUrl = "")
        )
    }
}
