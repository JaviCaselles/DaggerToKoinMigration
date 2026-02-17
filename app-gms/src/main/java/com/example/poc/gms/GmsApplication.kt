package com.example.poc.gms

import android.content.Context
import com.example.poc.BaseApplication
import com.example.poc.common.di.DI
import com.example.poc.common.di.DaggerDependencyProvider
import com.example.poc.common.di.HybridDependencyProvider
import com.example.poc.common.di.KoinDependencyProvider
import com.example.poc.common.di.ViewModelFactory
import com.example.poc.data.api.di.networkKoinModule
import com.example.poc.di.components.AppComponent
import com.example.poc.di.modules.ApiModule
import com.example.poc.di.modules.AppModule
import com.example.poc.di.modules.UseCaseModule
import com.example.poc.data.di.RepositoryModule
import com.example.poc.data.di.repositoryKoinModule
import com.example.poc.data.repository.ProductRepository
import com.example.poc.data.repository.UserRepository
import com.example.poc.gms.di.components.DaggerStradivariusGoogleMarketAppComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class GmsApplication : BaseApplication() {

    private lateinit var appComponent: AppComponent

    override fun getAppComponent(): AppComponent = appComponent

    override fun injectApplication() {
        val component = DaggerStradivariusGoogleMarketAppComponent.builder()
            .application(this)
            .apiModule(ApiModule())
            .appModule(AppModule())
            .repositoryModule(RepositoryModule())
            .useCaseModule(UseCaseModule())
            .build()
        
        component.inject(this)
        appComponent = component

        // Inicializar Dagger Provider (Legacy/Fallback)
        val daggerProvider = DaggerDependencyProvider().configure {
            register(Context::class.java) { this@GmsApplication }
            register(UserRepository::class.java) { component.userRepository() }
            register(ProductRepository::class.java) { component.productRepository() }
            register(ViewModelFactory::class.java) { component.viewModelFactory() }
        }

        // Inicializar Koin
        startKoin {
            androidContext(this@GmsApplication)
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
}

