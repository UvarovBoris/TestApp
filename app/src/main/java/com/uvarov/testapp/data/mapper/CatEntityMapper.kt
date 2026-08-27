package com.uvarov.testapp.data.mapper

import com.uvarov.testapp.data.local.BreedEntity
import com.uvarov.testapp.data.local.CatBreedCrossRef
import com.uvarov.testapp.data.local.CatEntity
import com.uvarov.testapp.data.local.CatWithBreeds
import com.uvarov.testapp.data.model.Breed
import com.uvarov.testapp.data.model.Cat

fun CatWithBreeds.toCat(): Cat = Cat(
    id = cat.id,
    name = cat.name,
    imageUrl = cat.imageUrl,
    breeds = breeds.map { it.toModel() }
)

fun Cat.toEntity(): CatEntity = CatEntity(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun Breed.toEntity(): BreedEntity = BreedEntity(
    id = id,
    name = name,
    temperament = temperament,
    origin = origin,
    lifeSpan = lifeSpan,
    description = description
)

fun BreedEntity.toModel(): Breed = Breed(
    id = id,
    name = name,
    temperament = temperament,
    origin = origin,
    lifeSpan = lifeSpan,
    description = description
)

fun Cat.toCrossRefs(): List<CatBreedCrossRef> = breeds.map { CatBreedCrossRef(catId = id, breedId = it.id) }
