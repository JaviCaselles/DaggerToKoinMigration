package com.example.poc

import android.app.Application
import com.example.poc.common.di.DI
import com.example.poc.common.di.KoinDependencyProvider

/**
 * Base Application class.
 * Provides common initialization for both GMS and HMS flavors.
 * Now fully powered by Koin — no Dagger dependency.
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        DI.initialize(KoinDependencyProvider())
    }

    /**
     * Subclasses must call startKoin with their specific module list.
     */
    abstract fun initializeKoin()
}
