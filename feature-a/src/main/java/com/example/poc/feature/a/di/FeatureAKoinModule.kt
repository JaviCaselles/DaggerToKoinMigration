package com.example.poc.feature.a.di

import com.example.poc.feature.a.domain.GetUsersUseCase
import com.example.poc.feature.a.ui.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAKoinModule = module {
    factory { GetUsersUseCase(get()) }
    viewModel { UserListViewModel(get()) }
}
