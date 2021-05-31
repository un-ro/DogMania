package com.unero.dogmania.loved

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lovedModule = module {
    viewModel { LovedViewModel(get()) }
}