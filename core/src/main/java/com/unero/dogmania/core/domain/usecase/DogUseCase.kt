package com.unero.dogmania.core.domain.usecase

import com.unero.dogmania.core.data.Resource
import com.unero.dogmania.core.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface DogUseCase {
    fun getRandom(): Flow<Resource<List<Dog>>>
    fun getFavorites(): Flow<List<Dog>>
    fun setFavorite(dog: Dog, state: Boolean)
}