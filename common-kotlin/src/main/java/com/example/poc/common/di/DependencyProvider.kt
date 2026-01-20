package com.example.poc.common.di

/**
 * Interfaz agnóstica del framework de DI.
 *
 * Esta interfaz define el contrato para resolver dependencias sin importar
 * si usamos Dagger, Koin, o cualquier otro framework.
 *
 * Beneficios:
 * - Desacopla el código del framework específico
 * - Permite migración incremental
 * - Facilita testing con mocks
 */
interface DependencyProvider {

    /**
     * Obtiene una instancia del tipo especificado.
     * @throws IllegalArgumentException si no existe resolver para el tipo
     */
    fun <T : Any> get(clazz: Class<T>): T

    /**
     * Obtiene una instancia o null si no existe.
     * Útil para dependencias opcionales.
     */
    fun <T : Any> getOrNull(clazz: Class<T>): T?

    /**
     * Obtiene una dependencia de forma lazy.
     * La instancia se crea solo cuando se accede por primera vez.
     */
    fun <T : Any> getLazy(clazz: Class<T>): Lazy<T>

    /**
     * Obtiene una dependencia con un qualifier/nombre específico.
     * Útil cuando hay múltiples implementaciones del mismo tipo.
     */
    fun <T : Any> get(clazz: Class<T>, qualifier: String): T
}

// Extension functions para sintaxis más limpia (reified generics)
inline fun <reified T : Any> DependencyProvider.get(): T = get(T::class.java)
inline fun <reified T : Any> DependencyProvider.getOrNull(): T? = getOrNull(T::class.java)
inline fun <reified T : Any> DependencyProvider.inject(): Lazy<T> = getLazy(T::class.java)
inline fun <reified T : Any> DependencyProvider.get(qualifier: String): T = get(T::class.java, qualifier)
