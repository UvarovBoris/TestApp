package com.uvarov.testapp.data.repository

import com.uvarov.testapp.data.model.Cat

interface CatRepository {
    suspend fun getCats(): List<Cat>
}
