package com.example.poc.di

import com.example.poc.common.di.DependencyInjectable
import com.example.poc.common.di.DependencyInjector
import com.example.poc.di.components.AppComponent

/**
 * DIManager principal.
 * 
 * Este es el patrón que usamos en producción: un singleton companion object
 * que implementa tanto DependencyInjector como DependencyInjectable.
 * 
 * Permite a cualquier parte del código obtener dependencias sin tener
 * referencia directa a la Application:
 * 
 *   val repo = DIManager.getAppComponent().userRepository()
 * 
 * NOTA: Este patrón será reemplazado por DI.get<T>() en fases posteriores.
 */
class DIManager {

    companion object : DependencyInjector<AppComponent>, DependencyInjectable<AppComponent> {

        private var dependencyInjector: DependencyInjector<AppComponent>? = null

        @JvmStatic
        override fun setDependencyInjector(dependencyInjector: DependencyInjector<AppComponent>) {
            this.dependencyInjector = dependencyInjector
        }

        @JvmStatic
        override fun getAppComponent(): AppComponent =
            dependencyInjector?.getAppComponent() ?: super.getAppComponent()

        @JvmStatic
        override fun clear() {
            dependencyInjector?.clear()
        }
    }
}
