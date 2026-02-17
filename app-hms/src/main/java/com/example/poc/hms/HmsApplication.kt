package com.example.poc.hms

import com.example.poc.BaseApplication
import com.example.poc.data.api.di.networkKoinModule
import com.example.poc.data.di.repositoryKoinModule
import com.example.poc.feature.a.di.featureAKoinModule
import com.example.poc.feature.b.di.featureBKoinModule
import com.example.poc.feature.c.di.featureCKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HmsApplication : BaseApplication() {

    override fun initializeKoin() {
        startKoin {
            androidContext(this@HmsApplication)
            modules(
                networkKoinModule,
                repositoryKoinModule,
                featureAKoinModule,
                featureBKoinModule,
                featureCKoinModule,
            )
        }
    }
}
