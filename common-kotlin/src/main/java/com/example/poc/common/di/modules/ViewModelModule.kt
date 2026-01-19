package com.example.poc.common.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.poc.common.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
