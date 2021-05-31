package com.unero.dogmania.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unero.dogmania.core.data.source.local.entity.DogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {

    @Query("SELECT * FROM dog")
    fun getAll(): Flow<List<DogEntity>>

    @Query("SELECT * FROM dog where isFavorite = 1")
    fun getFavorites(): Flow<List<DogEntity>>

    // Insert first time after fetching data from network
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(dogs: List<DogEntity>)

    @Query("DELETE FROM dog")
    suspend fun deleteAll()
}