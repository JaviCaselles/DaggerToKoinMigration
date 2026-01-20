package com.example.poc.common.di

/**
 * Provider híbrido que coordina la resolución entre Koin (primario) y otro provider (secundario/Dagger).
 *
 * Estrategia:
 * 1. Intenta resolver con Koin.
 * 2. Si Koin falla (no definition found), intenta con el provider secundario.
 * 3. Si ambos fallan, lanza excepción.
 */
class HybridDependencyProvider(
    private val koinProvider: DependencyProvider,
    private val secondaryProvider: DependencyProvider
) : DependencyProvider {

    override fun <T : Any> get(clazz: Class<T>): T {
        return try {
            // Intentar Koin primero
            koinProvider.get(clazz)
        } catch (e: Exception) {
            // Si falla, probar secundario (Dagger)
            try {
                secondaryProvider.get(clazz)
            } catch (secondaryError: Exception) {
                // Si ambos fallan, lanzar error compuesto o el original de Koin
                // Por simplicidad, lanzamos un mensaje explicativo
                throw IllegalArgumentException(
                    "Could not resolve ${clazz.simpleName} from Koin or Secondary Provider.",
                    secondaryError
                )
            }
        }
    }

    override fun <T : Any> get(clazz: Class<T>, qualifier: String): T {
        return try {
            koinProvider.get(clazz, qualifier)
        } catch (e: Exception) {
            secondaryProvider.get(clazz, qualifier)
        }
    }

    override fun <T : Any> getOrNull(clazz: Class<T>): T? {
        return koinProvider.getOrNull(clazz) ?: secondaryProvider.getOrNull(clazz)
    }

    override fun <T : Any> getLazy(clazz: Class<T>): Lazy<T> {
        return lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            get(clazz)
        }
    }
}
