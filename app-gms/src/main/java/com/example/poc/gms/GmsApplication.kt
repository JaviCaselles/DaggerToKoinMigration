package com.example.poc.gms

import com.example.poc.BaseApplication
import com.example.poc.di.components.AppComponent
import com.example.poc.di.modules.ApiModule
import com.example.poc.di.modules.AppModule
import com.example.poc.di.modules.UseCaseModule
import com.example.poc.data.di.RepositoryModule
import com.example.poc.gms.di.components.DaggerStradivariusGoogleMarketAppComponent



class GmsApplication : BaseApplication() {

    private lateinit var appComponent: AppComponent

    override fun getAppComponent(): AppComponent = appComponent

    override fun injectApplication() {
        val component = DaggerStradivariusGoogleMarketAppComponent.builder()
            .application(this)
            .appModule(AppModule())
            .apiModule(ApiModule())
            .repositoryModule(RepositoryModule())
            .useCaseModule(UseCaseModule())
            .build()
        
        component.inject(this)
        appComponent = component
    }
}
