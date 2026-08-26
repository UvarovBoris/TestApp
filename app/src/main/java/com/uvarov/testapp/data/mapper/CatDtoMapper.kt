package com.uvarov.testapp.data.mapper

import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.data.remote.dto.CatImageDto

fun CatImageDto.toCat(): Cat = Cat(
    id = id,
    name = id,
    imageUrl = url
)
