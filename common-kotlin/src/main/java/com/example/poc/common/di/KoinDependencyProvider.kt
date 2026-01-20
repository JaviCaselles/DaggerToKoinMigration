package com.example.poc.common.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.named

/**
 * Implementación de DependencyProvider basada en Koin.
 *
 * Utiliza la interfaz KoinComponent para resolver las dependencias
 * desde el contexto global de Koin.
 */
class KoinDependencyProvider : DependencyProvider, KoinComponent {

    override fun <T : Any> get(clazz: Class<T>): T {
        return getKoin().get(
            clazz = clazz.kotlin,
            qualifier = null,
            parameters = null
        )
    }

    override fun <T : Any> get(clazz: Class<T>, qualifier: String): T {
        return getKoin().get(
            clazz = clazz.kotlin,
            qualifier = named(qualifier),
            parameters = null
        )
    }

    override fun <T : Any> getOrNull(clazz: Class<T>): T? {
        return getKoin().getOrNull(
            clazz = clazz.kotlin,
            qualifier = null,
            parameters = null
        )
    }

    override fun <T : Any> getLazy(clazz: Class<T>): Lazy<T> {
        return lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            get(clazz)
        }
    }
}
