package com.example.poc.common.di

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DaggerDependencyProviderTest {

    private lateinit var dependencyProvider: DaggerDependencyProvider

    interface TestInterface
    class TestImplementation : TestInterface

    @Before
    fun setUp() {
        dependencyProvider = DaggerDependencyProvider()
    }

    @After
    fun tearDown() {
        DI.clear()
    }

    @Test
    fun `get returns registered dependency`() {
        val implementation = TestImplementation()
        dependencyProvider.register(TestInterface::class.java) { implementation }

        val result = dependencyProvider.get(TestInterface::class.java)

        assertEquals(implementation, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `get throws exception for unregistered dependency`() {
        dependencyProvider.get(TestInterface::class.java)
    }

    @Test
    fun `getOrNull returns null for unregistered dependency`() {
        val result = dependencyProvider.getOrNull(TestInterface::class.java)

        assertNull(result)
    }

    @Test
    fun `getLazy returns lazy instance`() {
        val implementation = TestImplementation()
        dependencyProvider.register(TestInterface::class.java) { implementation }

        val lazyResult = dependencyProvider.getLazy(TestInterface::class.java)

        assertTrue(lazyResult is Lazy<*>)
        assertEquals(implementation, lazyResult.value)
    }

    @Test
    fun `get with qualifier returns qualified dependency`() {
        val implementation = TestImplementation()
        val qualifier = "qualified"
        dependencyProvider.register(TestInterface::class.java, qualifier) { implementation }

        val result = dependencyProvider.get(TestInterface::class.java, qualifier)

        assertEquals(implementation, result)
    }

    @Test
    fun `DI initialize sets the provider`() {
        DI.initialize(dependencyProvider)
        val implementation = TestImplementation()
        dependencyProvider.register(TestInterface::class.java) { implementation }

        val result: TestInterface = DI.get()

        assertEquals(implementation, result)
    }
}
