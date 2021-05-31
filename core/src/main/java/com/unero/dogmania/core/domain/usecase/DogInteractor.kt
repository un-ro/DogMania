package com.unero.dogmania.core.domain.usecase

import com.unero.dogmania.core.domain.model.Dog
import com.unero.dogmania.core.domain.repository.IRepository

class DogInteractor(private val repository: IRepository): DogUseCase {
    override fun getRandom() = repository.getRandom()
    override fun getFavorites() = repository.getFavorites()
    override fun setFavorite(dog: Dog, state: Boolean) = repository.setFavorite(dog, state)
}