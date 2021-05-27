package com.unero.dogmania.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val id: Int,
    val image: String,
    val comment: String,
    val date: String,
    val isFavorite: Boolean
): Parcelable