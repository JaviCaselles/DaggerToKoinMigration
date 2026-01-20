package com.example.poc.common.di

/**
 * Implementación de DependencyProvider que puede configurarse externamente.
 * 
 * Es "Dagger" DependencyProvider porque se diseñó para envolver un componente Dagger,
 * pero técnicamente es un registro de proveedores.
 */
class DaggerDependencyProvider : DependencyProvider {

    // Mapa de resolvers: Class -> Lambda que obtiene la instancia
    private val resolvers = mutableMapOf<Class<*>, () -> Any>()
    private val qualifiedResolvers = mutableMapOf<Pair<Class<*>, String>, () -> Any>()

    /**
     * Registra un resolver para un tipo específico.
     *
     * Ejemplo:
     *   register(CartRepository::class.java) { component.cartRepository }
     */
    fun <T : Any> register(clazz: Class<T>, resolver: () -> T) {
        resolvers[clazz] = resolver
    }

    fun <T : Any> register(clazz: Class<T>, qualifier: String, resolver: () -> T) {
        qualifiedResolvers[clazz to qualifier] = resolver
    }

    /**
     * Builder pattern para registrar múltiples resolvers de forma fluida.
     */
    fun configure(block: DaggerDependencyProvider.() -> Unit): DaggerDependencyProvider {
        block()
        return this
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clazz: Class<T>): T {
        val resolver = resolvers[clazz]
            ?: throw IllegalArgumentException(
                "No resolver registered for ${clazz.simpleName}. " +
                        "Did you forget to call register() for this type?"
            )
        return resolver() as T
    }

    override fun <T : Any> getOrNull(clazz: Class<T>): T? =
        runCatching { get(clazz) }.getOrNull()

    override fun <T : Any> getLazy(clazz: Class<T>): Lazy<T> =
        lazy { get(clazz) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clazz: Class<T>, qualifier: String): T {
        val resolver = qualifiedResolvers[clazz to qualifier]
            ?: throw IllegalArgumentException(
                "No resolver for ${clazz.simpleName} with qualifier '$qualifier'"
            )
        return resolver() as T
    }
}
