package com.example.poc.common.di

/**
 * Interface para configurar el injector de dependencias.
 * Los DIManagers implementan esta interface para recibir el injector
 * desde la Application.
 */
interface DependencyInjectable<in Component> {

    /**
     * Configura el injector que proveerá el componente.
     * Debe llamarse desde Application.onCreate() antes de usar el DIManager.
     */
    fun setDependencyInjector(dependencyInjector: DependencyInjector<Component>)
}
