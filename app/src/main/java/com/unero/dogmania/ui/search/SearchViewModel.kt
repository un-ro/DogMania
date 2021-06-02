package com.unero.dogmania.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.unero.dogmania.core.data.source.remote.network.ApiResponse
import com.unero.dogmania.core.data.source.remote.response.RandomResponse
import com.unero.dogmania.core.domain.usecase.DogUseCase

class SearchViewModel(private val useCase: DogUseCase): ViewModel() {

    fun search(breed: String): LiveData<ApiResponse<RandomResponse>> {
        return useCase.getSearch(breed).asLiveData()
    }
}
