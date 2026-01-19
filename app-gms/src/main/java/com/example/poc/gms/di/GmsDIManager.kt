package com.example.poc.gms.di

import com.example.poc.common.di.DependencyInjectable
import com.example.poc.common.di.DependencyInjector
import com.example.poc.di.components.AppComponent

/**
 * DIManager específico para GMS.
 * Permite acceso al componente desde código específico de GMS.
 */
class GmsDIManager {
    
    companion object : DependencyInjector<AppComponent>, DependencyInjectable<AppComponent> {

        private var dependencyInjector: DependencyInjector<AppComponent>? = null

        override fun setDependencyInjector(dependencyInjector: DependencyInjector<AppComponent>) {
            this.dependencyInjector = dependencyInjector
        }

        override fun getAppComponent(): AppComponent =
            dependencyInjector?.getAppComponent() ?: super.getAppComponent()

        override fun clear() {
            dependencyInjector?.clear()
        }
    }
}
