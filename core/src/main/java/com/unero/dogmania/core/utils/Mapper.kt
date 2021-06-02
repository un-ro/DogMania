package com.unero.dogmania.core.utils

import com.unero.dogmania.core.data.source.local.entity.DogEntity
import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import com.unero.dogmania.core.domain.model.Dog

object Mapper {

    fun mapResponseToEntities(input: RandomResponse): List<DogEntity> {
        val dogList = ArrayList<DogEntity>()
        for (dog in input.images) {
            dogList.add(
                DogEntity(
                    id = 0,
                    image = dog,
                    comment = "",
                    date = "",
                    isFavorite = false
                )
            )
        }
        return dogList
    }

    fun mapResponseToDomain(input: RandomResponse): List<Dog> {
        val dogList = ArrayList<Dog>()
        for (dog in input.images) {
            dogList.add(
                Dog(
                    id = 0,
                    image = dog,
                    comment = "",
                    date = "",
                    isFavorite = false
                )
            )
        }
        return dogList
    }

    fun mapEntitiesToDomain(input: List<DogEntity>): List<Dog> =
        input.map {
            Dog (
                id = it.id,
                image = it.image,
                comment = it.comment,
                date = it.date,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Dog) =
        DogEntity(
            id = input.id,
            image = input.image,
            comment = input.comment,
            date = input.date,
            isFavorite = input.isFavorite
        )
}
