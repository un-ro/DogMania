package com.unero.dogmania.core.data.source.local

import com.unero.dogmania.core.data.source.local.entity.DogEntity
import com.unero.dogmania.core.data.source.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favoriteDao: FavoriteDao) {
    fun getAll(): Flow<List<DogEntity>> = favoriteDao.getAll()

    fun getFavorites(): Flow<List<DogEntity>> = favoriteDao.getFavorites()

    suspend fun insert(dogs: List<DogEntity>) = favoriteDao.insert(dogs)
}