# Hallazgos y Decisiones Técnicas: Implementación de DependencyProvider

Este documento resume los problemas encontrados y las soluciones aplicadas durante la implementación de la capa de abstracción `DependencyProvider` en el POC `dagger-koin-app`. Estos hallazgos son críticos para la migración de la aplicación real (`mob-strappandroid`).

## 1. Desacoplamiento de `DaggerDependencyProvider`

**Problema:**
Inicialmente, `DaggerDependencyProvider` dependía de `AppComponent` para resolver las dependencias. Esto creaba un acoplamiento circular potencial y hacía que el módulo `common-kotlin` dependiera del módulo `app-legacy`, lo cual no es ideal para una librería común.

**Solución:**
Refactorizamos `DaggerDependencyProvider` para que sea un **registro genérico**. No conoce `AppComponent`. En su lugar, recibe lambdas que saben cómo obtener la dependencia.

```kotlin
// Antes (Problemático)
class DaggerDependencyProvider(val component: AppComponent)

// Ahora (Correcto)
class DaggerDependencyProvider : DependencyProvider {
    fun register(clazz, provider: () -> Any)
}

// Uso en Application
provider.configure {
    register(Repo::class.java) { component.repository() } // El acoplamiento queda en la App, no en common
}
```

## 2. Exposición Explícita en `AppComponent`

**Problema:**
Dagger genera código solo para lo que necesita. Si una dependencia solo se usa internamente en un grafo de Dagger, no se expone públicamente en la interfaz del Componente. Sin embargo, `DependencyProvider` necesita acceder a estas instancias desde fuera del grafo de Dagger (en el código de la `Application` donde configuramos el provider).

**Solución:**
Tuvimos que añadir métodos de provisión explícitos en `AppComponent` para todas las dependencias que queremos migrar o usar via `DI.get()`.

```kotlin
interface AppComponent {
    // Necesario para que Dagger genere el getter público
    fun userRepository(): UserRepository
    fun apiService(): ApiService
    // ...
}
```

## 3. Herencia de Builders en Dagger

**Problema:**
Intentamos que `StradivariusGoogleMarketAppComponent.Builder` heredara de `AppComponent.Builder`.
```kotlin
interface Builder : AppComponent.Builder<Builder> // Error de tipos recursivos
```
Esto causó problemas de compilación porque los métodos fluidos (`apiModule(...)`) devolvían el tipo padre, rompiendo la cadena de llamadas para el builder hijo.

**Solución:**
Desacoplar los builders. Aunque hay duplicación de código (ambos builders declaran los mismos métodos), es más seguro y limpio definir explícitamente el builder completo en cada componente hoja (`Gms` y `Hms`).

## 4. Dependencias Transitivas en `app-hms`

**Problema:**
El módulo `app-hms` fallaba al compilar con errores de `Unresolved reference: Retrofit` y `ApiService`, aunque estas clases se usaban indirectamente a través del `AppComponent`.

**Solución:**
Aunque Dagger gestiona el grafo, las **clases** que se exponen en la interfaz del componente (como `Retrofit` o `ApiService` en los métodos que añadimos en el punto 2) deben estar visibles en el classpath del módulo consumidor (`app-hms`). Tuvimos que añadir explícitamente `retrofit`, `okhttp` y los módulos `data` al `build.gradle.kts` de `app-hms`.

## 5. Inyección en Clase Base (`BaseApplication`)

**Problema:**
`HmsApplication` fallaba porque Dagger no generaba un inyector para `BaseApplication`, y `HmsApplication` hereda inyecciones de ella.

**Solución:**
Añadimos explícitamente un método de inyección para la clase base en el componente.

```kotlin
interface AppComponent {
    fun inject(application: BaseApplication)
}
```
Esto permite que las subclases deleguen la inyección de los miembros heredados correctamente.

## Resumen del Checklist Para Migración Real

Al aplicar esto en `mob-strappandroid`, debemos asegurar:

1.  [ ] **`common-kotlin`** debe ser agnóstica de Dagger/Koin.
2.  [ ] **`AppComponent`** debe exponer explícitamente todo lo que se vaya a migrar.
3.  [ ] **`build.gradle`** de los flavors debe tener acceso a las clases de las dependencias expuestas.
4.  [ ] **Builders** de Dagger deben ser autónomos en los componentes hoja si hay conflictos de herencia.
