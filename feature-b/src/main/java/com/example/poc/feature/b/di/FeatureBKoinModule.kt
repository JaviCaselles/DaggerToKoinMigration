package com.example.poc.feature.b.di

import com.example.poc.feature.b.domain.GetProductsUseCase
import com.example.poc.feature.b.ui.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureBKoinModule = module {
    factory { GetProductsUseCase(get()) }
    viewModel { ProductListViewModel(get()) }
}
