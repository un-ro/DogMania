package com.unero.dogmania.loved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.unero.dogmania.core.domain.usecase.DogUseCase

class LovedViewModel(useCase: DogUseCase): ViewModel() {
    val favorites = useCase.getFavorites().asLiveData()
}