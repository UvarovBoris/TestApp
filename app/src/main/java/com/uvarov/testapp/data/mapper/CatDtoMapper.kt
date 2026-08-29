package com.uvarov.testapp.data.mapper

import com.uvarov.testapp.data.remote.dto.BreedDto
import com.uvarov.testapp.data.remote.dto.CatImageDto
import com.uvarov.testapp.domain.model.Breed
import com.uvarov.testapp.domain.model.Cat

fun CatImageDto.toCat(): Cat = Cat(
    id = id,
    name = id,
    imageUrl = url,
    breeds = breeds.orEmpty().map { it.toModel() }
)

fun BreedDto.toModel(): Breed = Breed(
    id = id,
    name = name,
    temperament = temperament,
    origin = origin,
    lifeSpan = lifeSpan,
    description = description
)
