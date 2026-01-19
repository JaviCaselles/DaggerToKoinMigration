package com.example.poc.common.di

/**
 * Interface genérica para obtener el componente de DI.
 * Esto permite que cualquier módulo obtenga su componente sin saber
 * qué framework de DI se usa internamente.
 */
interface DependencyInjector<out Component> {

    companion object {
        private const val NO_DEPENDENCY_INJECTOR_EXCEPTION = "No dependency injector set"
    }

    /**
     * Obtiene el componente de inyección de dependencias.
     * Lanza excepción si no se ha configurado ningún injector.
     */
    fun getAppComponent(): Component = 
        throw Exception(NO_DEPENDENCY_INJECTOR_EXCEPTION)

    /**
     * Limpia el grafo de dependencias.
     * Útil cuando el usuario hace logout o se necesita recrear el grafo.
     */
    fun clear() {
        // no-op por defecto
    }
}
