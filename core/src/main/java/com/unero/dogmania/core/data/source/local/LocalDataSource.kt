package com.unero.dogmania.core.data.source.local

import com.unero.dogmania.core.data.source.local.entity.DogEntity
import com.unero.dogmania.core.data.source.local.room.DogDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favoriteDao: DogDao) {
    fun getAll(): Flow<List<DogEntity>> = favoriteDao.getAll()

    fun getFavorites(): Flow<List<DogEntity>> = favoriteDao.getFavorites()

    suspend fun insertAll(dogs: List<DogEntity>) = favoriteDao.insertAll(dogs)

    suspend fun deleteAll() = favoriteDao.deleteAll()

    fun setFavorite(dog: DogEntity, newState: Boolean) {
        dog.isFavorite = newState
        favoriteDao.update(dog)
    }
}