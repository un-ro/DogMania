package com.unero.dogmania

import com.unero.dogmania.core.domain.model.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object Faker {
    fun dummyDogs(): Flow<List<Dog>> {
        return flow {
            listOf(
                dog, dog, dog, dog, dog
            )
        }
    }

    private val dog = Dog(
        0,
        "https://images.dog.ceo/breeds/hound-afghan/n02088094_10982.jpg",
        "",
        "",
        false
    )
}