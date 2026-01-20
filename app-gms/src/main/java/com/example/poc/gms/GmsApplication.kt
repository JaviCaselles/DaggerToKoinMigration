package com.example.poc.gms

import android.content.Context
import com.example.poc.BaseApplication
import com.example.poc.common.di.DI
import com.example.poc.common.di.DaggerDependencyProvider
import com.example.poc.common.di.HybridDependencyProvider
import com.example.poc.common.di.KoinDependencyProvider
import com.example.poc.common.di.ViewModelFactory
import com.example.poc.data.api.ApiService
import com.example.poc.di.components.AppComponent
import com.example.poc.di.modules.ApiModule
import com.example.poc.di.modules.AppModule
import com.example.poc.di.modules.UseCaseModule
import com.example.poc.data.di.RepositoryModule
import com.example.poc.data.di.repositoryKoinModule
import com.example.poc.data.repository.ProductRepository
import com.example.poc.data.repository.UserRepository
import com.example.poc.gms.di.components.DaggerStradivariusGoogleMarketAppComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


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
            register(ApiService::class.java) { component.apiService() }
            register(UserRepository::class.java) { component.userRepository() }
            register(ProductRepository::class.java) { component.productRepository() }
            register(OkHttpClient::class.java) { component.okHttpClient() }
            register(Retrofit::class.java) { component.retrofit() }
            register(ViewModelFactory::class.java) { component.viewModelFactory() }
        }

        // Inicializar Koin
        startKoin {
            androidContext(this@GmsApplication)
            modules(
                repositoryKoinModule,
                // Legacy Bridge: Expose Dagger deps to Koin
                module {
                    single { component.apiService() }
                }
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
