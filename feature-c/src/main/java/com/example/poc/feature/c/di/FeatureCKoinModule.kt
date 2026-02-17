package com.example.poc.feature.c.di

import com.example.poc.feature.c.domain.AggregatedUseCase
import com.example.poc.feature.c.ui.AggregatedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureCKoinModule = module {
    factory { AggregatedUseCase(get(), get()) }
    viewModel { AggregatedViewModel(get()) }
}
