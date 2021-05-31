package com.unero.dogmania.core.domain.repository

import com.unero.dogmania.core.data.Resource
import com.unero.dogmania.core.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun getRandom(): Flow<Resource<List<Dog>>>
    fun getFavorites(): Flow<List<Dog>>
    fun setFavorite(dog: Dog, state: Boolean)
}