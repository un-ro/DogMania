package com.unero.dogmania.di

import com.unero.dogmania.adapter.ItemAdapter
import com.unero.dogmania.core.domain.usecase.DogInteractor
import com.unero.dogmania.core.domain.usecase.DogUseCase
import com.unero.dogmania.ui.detail.DetailViewModel
import com.unero.dogmania.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<DogUseCase> { DogInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val adapterModule = module {
    single { ItemAdapter(get()) }
}