package com.example.poc.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ApiModule::class,
    UseCaseModule::class,
    com.example.poc.common.di.modules.ViewModelModule::class
])
open class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}
