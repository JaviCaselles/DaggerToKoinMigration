package com.example.poc.hms

import com.example.poc.BaseApplication
import com.example.poc.di.components.AppComponent
import com.example.poc.di.components.DaggerAppComponent
import com.example.poc.di.modules.ApiModule

import com.example.poc.di.modules.UseCaseModule
import com.example.poc.hms.di.HmsDIManager

/**
 * Application para el flavor HMS (Huawei Mobile Services).
 * 
 * Equivalente a StradivariusHMSApplication en el proyecto real.
 */
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
        getAppComponent().inject(this)
    }

    override fun setupDependencyInjection() {
        super.setupDependencyInjection()
        // DIManagers específicos de HMS
        HmsDIManager.setDependencyInjector(
            object : com.example.poc.common.di.DependencyInjector<AppComponent> {
                override fun getAppComponent() = this@HmsApplication.getAppComponent()
            }
        )
    }

    override fun onDependencyInjectionComplete() {
        super.onDependencyInjectionComplete()
        // Inicializaciones específicas de HMS
        // Por ejemplo: Huawei Push Kit, Analytics, etc.
    }

    /**
     * Limpia el grafo de Dagger y lo recrea.
     * Útil para logout o cambios de configuración.
     */
    fun clear() {
        daggerComponent = null
        getAppComponent() // Recrear
    }
}

