package com.uvarov.testapp.data.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CatWithBreeds(
    @Embedded
    val cat: CatEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CatBreedCrossRef::class,
            parentColumn = "catId",
            entityColumn = "breedId"
        )
    )
    val breeds: List<BreedEntity>
)
