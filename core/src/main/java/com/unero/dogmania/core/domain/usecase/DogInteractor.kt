package com.unero.dogmania.core.domain.usecase

import com.unero.dogmania.core.domain.repository.IRepository

class DogInteractor(private val repository: IRepository): DogUseCase {
    override fun getAll() = repository.getAll()

    override fun getFavorites() = repository.getFavorites()
}