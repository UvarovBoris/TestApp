package com.uvarov.testapp.data.mapper

import com.uvarov.testapp.data.local.CatEntity
import com.uvarov.testapp.data.model.Cat

fun CatEntity.toCat(): Cat = Cat(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun Cat.toEntity(): CatEntity = CatEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)
