package com.example.poc.common.di

/**
 * Punto de acceso global a la inyección de dependencias.
 *
 * Uso:
 *   val repo: CartRepository = DI.get()
 *   val lazy: CartRepository by DI.inject()
 */
object DI {

    private var provider: DependencyProvider? = null

    /**
     * Inicializa el provider. Llamar una vez en Application.onCreate()
     */
    fun initialize(provider: DependencyProvider) {
        this.provider = provider
    }

    /**
     * Limpia el provider. Útil para tests y cuando se recrea el grafo.
     */
    fun clear() {
        provider = null
    }

    @PublishedApi
    internal fun requireProvider(): DependencyProvider =
        provider ?: throw IllegalStateException(
            "DI not initialized. Call DI.initialize() in Application.onCreate()"
        )

    // Delegamos al provider
    inline fun <reified T : Any> get(): T = requireProvider().get()
    inline fun <reified T : Any> getOrNull(): T? = requireProvider().getOrNull()
    inline fun <reified T : Any> inject(): Lazy<T> = requireProvider().inject()
    inline fun <reified T : Any> get(qualifier: String): T = requireProvider().get(qualifier)
}
