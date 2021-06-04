package com.unero.dogmania.core

import com.unero.dogmania.core.domain.repository.IRepository
import com.unero.dogmania.core.domain.usecase.DogInteractor
import com.unero.dogmania.core.domain.usecase.DogUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DogUseCaseTest {
    private lateinit var useCase: DogUseCase

    @Mock
    private lateinit var repository: IRepository

    @Before
    fun setUp(){
        useCase = DogInteractor(repository)
        val dummyDog = Faker.dummyDogs()
        Mockito.`when`(repository.getFavorites()).thenReturn(dummyDog)
    }

    @Test
    fun `Get Empty Favorite as Flow`(){
        val response = useCase.getFavorites()
        Mockito.verify(repository).getFavorites()
        Mockito.verifyNoMoreInteractions(repository)
        println(response)
    }
}