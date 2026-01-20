package com.example.poc.data.di

import com.example.poc.data.repository.UserRepository
import com.example.poc.data.repository.UserRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryKoinModule = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}
