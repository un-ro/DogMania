package com.unero.dogmania.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.unero.dogmania.core.domain.usecase.DogUseCase
import kotlinx.coroutines.runBlocking

class HomeViewModel(dogUseCase: DogUseCase): ViewModel() {
    val dog = runBlocking {
        dogUseCase.getRandom().asLiveData()
    }
}