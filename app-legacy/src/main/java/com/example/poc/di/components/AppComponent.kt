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
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Componente Dagger principal.
 * Define todas las dependencias que pueden ser inyectadas y expuestas.
 * 
 * IMPORTANTE: Este componente extiende los componentes de los feature modules
 * para que puedan acceder a las dependencias base.
 */
interface AppComponent {




    // ===== Builder =====
    interface Builder<ComponentBuilder> {
        fun apiModule(apiModule: ApiModule): ComponentBuilder
        fun appModule(appModule: AppModule): ComponentBuilder
        fun repositoryModule(repositoryModule: RepositoryModule): ComponentBuilder
        fun useCaseModule(useCaseModule: UseCaseModule): ComponentBuilder
    }
}
