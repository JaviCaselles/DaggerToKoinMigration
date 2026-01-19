package com.example.poc.gms.di.components

import android.app.Application
import com.example.poc.gms.GmsApplication
import com.example.poc.data.di.RepositoryModule
import com.example.poc.di.components.AppComponent
import com.example.poc.di.modules.ApiModule
import com.example.poc.di.modules.AppModule
import com.example.poc.di.modules.UseCaseModule
import com.example.poc.feature.a.di.FeatureAModule
import com.example.poc.feature.b.di.FeatureBModule
import com.example.poc.feature.c.di.FeatureCModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class, // AppModule includes Api, UseCase
        RepositoryModule::class, // Explicitly included in Reference
        FeatureAModule::class,
        FeatureBModule::class,
        FeatureCModule::class
    ]
)
interface StradivariusGoogleMarketAppComponent : AppComponent {

    fun inject(application: GmsApplication)

    @Component.Builder
    interface Builder : AppComponent.Builder<Builder> {
        @BindsInstance
        fun application(application: GmsApplication): Builder
        
        fun build(): StradivariusGoogleMarketAppComponent
    }
}
