package com.unero.dogmania.core.data

import com.unero.dogmania.core.data.source.local.LocalDataSource
import com.unero.dogmania.core.data.source.remote.RemoteDataSource
import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import com.unero.dogmania.core.domain.model.Dog
import com.unero.dogmania.core.domain.repository.IRepository
import com.unero.dogmania.core.utils.AppExecutors
import com.unero.dogmania.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IRepository {
    override fun getRandom(): Flow<Resource<List<Dog>>> =
        object : NetworkBoundResource<List<Dog>, RandomResponse>(appExecutors) {
            override fun loadFromDB(): Flow<List<Dog>> {
                return localDataSource.getAll().map {
                    Mapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Dog>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<RandomResponse>> =
                remoteDataSource.getRandom()

            override suspend fun saveCallResult(data: RandomResponse) {
                val dogList = Mapper.mapResponseToEntities(data)
                localDataSource.deleteAll()
                localDataSource.insertAll(dogList)
            }

        }.asFlow()

    override fun getSearch(breed: String): Flow<ApiResponse<RandomResponse>> {
        return runBlocking {
            remoteDataSource.getSearch(breed)
        }
    }

    override fun getFavorites(): Flow<List<Dog>> {
        return localDataSource.getFavorites().map {
            Mapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavorite(dog: Dog, state: Boolean) {
        val entity = Mapper.mapDomainToEntity(dog)
        appExecutors.diskIO().execute { localDataSource.setFavorite(entity, state) }
    }

    override fun setComment(dog: Dog, comment: String) {
        val entity = Mapper.mapDomainToEntity(dog)
        appExecutors.diskIO().execute { localDataSource.setComment(entity, comment)}
    }
}