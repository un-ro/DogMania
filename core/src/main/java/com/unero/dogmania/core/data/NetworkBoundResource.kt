package com.unero.dogmania.core.data

import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.core.data.source.remote.network.ApiResponse.*
import kotlinx.coroutines.flow.*

@Suppress("RemoveExplicitTypeArguments")
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map {
                        Resource.Success(it)
                    })
                }
                is Empty -> {
                    emitAll(loadFromDB().map {
                        Resource.Success(it)
                    })
                }
                is Error -> {
                    onFetchFailed()
                    emit(
                        Resource.Error<ResultType>(apiResponse.errorMessage)
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map {
                Resource.Success(it)
            })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}