package com.uvarov.testapp.core.data.mapper

import com.uvarov.testapp.core.database.entity.BreedEntity
import com.uvarov.testapp.core.database.entity.CatBreedCrossRef
import com.uvarov.testapp.core.database.entity.CatEntity
import com.uvarov.testapp.core.database.entity.CatWithBreeds
import com.uvarov.testapp.domain.model.Breed
import com.uvarov.testapp.domain.model.Cat

fun CatWithBreeds.toCat(): Cat = Cat(
    id = cat.id,
    imageUrl = cat.imageUrl,
    breeds = breeds.map { it.toModel() }
)

fun Cat.toEntity(): CatEntity = CatEntity(
    id = id,
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
