package com.example.poc.data.di

import com.example.poc.data.api.ApiService
import com.example.poc.data.repository.ProductRepository
import com.example.poc.data.repository.ProductRepositoryImpl
import com.example.poc.data.repository.UserRepository
import com.example.poc.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }
}
