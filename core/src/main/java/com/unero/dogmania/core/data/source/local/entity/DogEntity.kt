package com.unero.dogmania.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog")
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    var comment: String = "",
    var date: String = "",
    var isFavorite: Boolean = false
)
