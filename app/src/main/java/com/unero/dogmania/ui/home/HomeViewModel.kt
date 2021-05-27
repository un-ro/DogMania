package com.unero.dogmania.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.unero.dogmania.core.domain.usecase.DogUseCase

class HomeViewModel(dogUseCase: DogUseCase): ViewModel() {
    val dog = dogUseCase.getAll().asLiveData()
}