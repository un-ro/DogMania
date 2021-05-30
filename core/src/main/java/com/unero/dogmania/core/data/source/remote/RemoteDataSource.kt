package com.unero.dogmania.core.data.source.remote

import android.util.Log
import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.core.data.source.remote.network.Endpoint
import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val endpoint: Endpoint) {
    suspend fun getRandom(): Flow<ApiResponse<RandomResponse>> {
        return flow {
            try {
                val response = endpoint.getRandomFor()
                if (response.status == "success") {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}