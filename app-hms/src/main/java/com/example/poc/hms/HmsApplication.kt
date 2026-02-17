package com.example.poc.hms

import android.content.Context
import com.example.poc.BaseApplication
import com.example.poc.common.di.DI
import com.example.poc.common.di.DaggerDependencyProvider
import com.example.poc.common.di.DependencyInjector
import com.example.poc.common.di.HybridDependencyProvider
import com.example.poc.common.di.KoinDependencyProvider
import com.example.poc.common.di.ViewModelFactory
import com.example.poc.data.api.di.networkKoinModule
import com.example.poc.data.di.repositoryKoinModule
import com.example.poc.di.components.AppComponent
import com.example.poc.di.components.DaggerAppComponent
import com.example.poc.di.modules.ApiModule
import com.example.poc.hms.di.HmsDIManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HmsApplication : BaseApplication() {

    private var daggerComponent: AppComponent? = null

    override fun getAppComponent(): AppComponent {
        return daggerComponent ?: createComponent().also { daggerComponent = it }
    }

    private fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .application(this)
            .apiModule(ApiModule())
            .build()
    }

    override fun injectApplication() {
        val component = getAppComponent()
        component.inject(this)

        // Inicializar Dagger Provider (Legacy/Fallback)
        val daggerProvider = DaggerDependencyProvider().configure {
            register(Context::class.java) { this@HmsApplication }
            register(ViewModelFactory::class.java) { component.viewModelFactory() }
        }

        // Inicializar Koin
        startKoin {
            androidContext(this@HmsApplication)
            modules(
                networkKoinModule,
                repositoryKoinModule,
            )
        }

        // Configurar Hybrid Provider (Koin -> Dagger)
        val koinProvider = KoinDependencyProvider()
        val hybridProvider = HybridDependencyProvider(
            koinProvider = koinProvider,
            secondaryProvider = daggerProvider
        )

        DI.initialize(hybridProvider)
    }

    override fun setupDependencyInjection() {
        super.setupDependencyInjection()
        // DIManagers específicos de HMS
        HmsDIManager.setDependencyInjector(
            object : DependencyInjector<AppComponent> {
                override fun getAppComponent() = this@HmsApplication.getAppComponent()
            }
        )
    }

    override fun onDependencyInjectionComplete() {
        super.onDependencyInjectionComplete()
    }

    fun clear() {
        daggerComponent = null
        getAppComponent()
    }
}
