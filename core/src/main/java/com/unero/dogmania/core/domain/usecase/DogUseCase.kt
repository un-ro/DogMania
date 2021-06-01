package com.unero.dogmania.core.domain.usecase

import com.unero.dogmania.core.data.Resource
import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import com.unero.dogmania.core.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface DogUseCase {
    fun getRandom(): Flow<Resource<List<Dog>>>
    fun getSearch(breed: String): Flow<ApiResponse<RandomResponse>>

    fun getFavorites(): Flow<List<Dog>>
    fun setFavorite(dog: Dog, state: Boolean)
    fun setComment(dog: Dog, comment: String)
}