package com.unero.dogmania.core.domain.repository

import com.unero.dogmania.core.data.Resource
import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import com.unero.dogmania.core.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface IRepository {

    suspend fun getRandom(): Flow<ApiResponse<RandomResponse>>

    fun getAll(): Flow<Resource<List<Dog>>>

    fun getFavorites(): Flow<List<Dog>>
}