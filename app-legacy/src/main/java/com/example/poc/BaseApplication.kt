package com.example.poc

import android.app.Application
import com.example.poc.common.di.DependencyInjector
import com.example.poc.di.DIManager
import com.example.poc.di.components.AppComponent

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Application base que configura Dagger.
 * 
 * Equivalente a InditexApplication en el proyecto real.
 * Las applications concretas (GMS/HMS) heredan de esta.
 */
abstract class BaseApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    /**
     * Template method para obtener el componente Dagger.
     * Las subclases implementan esto con su componente específico.
     */
    abstract fun getAppComponent(): AppComponent

    /**
     * Template method para inyectar la Application.
     */
    abstract fun injectApplication()

    override fun onCreate() {
        super.onCreate()
        setupDependencyInjection()
        injectApplication()
        onDependencyInjectionComplete()
    }

    /**
     * Configura todos los DIManagers.
     * Las subclases pueden override para añadir más DIManagers.
     */
    protected open fun setupDependencyInjection() {
        val injector = createDependencyInjector()
        
        // Configurar DIManagers
        DIManager.setDependencyInjector(injector)
    }

    /**
     * Crea el injector que envuelve el componente Dagger.
     */
    private fun createDependencyInjector(): DependencyInjector<AppComponent> {
        return object : DependencyInjector<AppComponent> {
            override fun getAppComponent(): AppComponent = this@BaseApplication.getAppComponent()
            override fun clear() {
                // Limpiar el componente si es necesario
            }
        }
    }

    /**
     * Hook que se llama después de configurar DI.
     * Útil para inicializaciones que requieren dependencias.
     */
    protected open fun onDependencyInjectionComplete() {
        // Override en subclases si se necesita
    }
}
