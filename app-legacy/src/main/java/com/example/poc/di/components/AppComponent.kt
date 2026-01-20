package com.example.poc.di.components

import com.example.poc.BaseApplication
import com.example.poc.data.api.ApiService
import com.example.poc.di.modules.AppModule
import com.example.poc.di.modules.ApiModule
import com.example.poc.data.di.RepositoryModule
import com.example.poc.di.modules.UseCaseModule
import com.example.poc.data.repository.ProductRepository
import com.example.poc.data.repository.UserRepository
import com.example.poc.feature.a.di.FeatureAModule
import com.example.poc.feature.b.di.FeatureBModule
import com.example.poc.feature.c.di.FeatureCModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import android.app.Application
import com.example.poc.common.di.ViewModelFactory
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Componente Dagger principal.
 * Define todas las dependencias que pueden ser inyectadas y expuestas.
 * 
 * IMPORTANTE: Este componente extiende los componentes de los feature modules
 * para que puedan acceder a las dependencias base.
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        RepositoryModule::class,
        ApiModule::class,
        UseCaseModule::class,
        FeatureAModule::class,
        FeatureBModule::class,
        FeatureCModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent {

    fun inject(application: BaseApplication)

    // Expose dependencies for DaggerDependencyProvider (Bridge)
    fun application(): Application
    fun apiService(): ApiService
    fun userRepository(): UserRepository
    fun productRepository(): ProductRepository
    fun okHttpClient(): OkHttpClient
    fun retrofit(): Retrofit
    fun viewModelFactory(): ViewModelFactory

    // ===== Builder =====
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: android.app.Application): Builder

        fun apiModule(apiModule: ApiModule): Builder
        fun appModule(appModule: AppModule): Builder
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
        fun useCaseModule(useCaseModule: UseCaseModule): Builder

        fun build(): AppComponent
    }
}
