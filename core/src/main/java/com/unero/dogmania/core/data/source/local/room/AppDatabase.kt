package com.unero.dogmania.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unero.dogmania.core.data.source.local.entity.DogEntity

@Database(entities = [DogEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDao(): DogDao
}