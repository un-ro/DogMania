package com.unero.dogmania.ui.detail

import androidx.lifecycle.ViewModel
import com.unero.dogmania.core.domain.model.Dog
import com.unero.dogmania.core.domain.usecase.DogUseCase

class DetailViewModel(private val useCase: DogUseCase) : ViewModel() {
    fun setFavorite(dog: Dog, state: Boolean) = useCase.setFavorite(dog, state)
}