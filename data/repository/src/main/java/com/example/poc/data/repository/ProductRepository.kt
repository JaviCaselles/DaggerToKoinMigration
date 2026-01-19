package com.example.poc.data.repository

import com.example.poc.data.api.ApiService
import com.example.poc.data.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProduct(id: String): Product?
}

internal class ProductRepositoryImpl(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return try {
            apiService.getProducts()
        } catch (e: Exception) {
            // Datos mock para el POC
            listOf(
                Product("1", "Camiseta Básica", 19.99, "https://example.com/img1.jpg"),
                Product("2", "Pantalón Vaquero", 49.99, "https://example.com/img2.jpg"),
                Product("3", "Vestido Largo", 79.99, "https://example.com/img3.jpg")
            )
        }
    }

    override suspend fun getProduct(id: String): Product? {
        return getProducts().find { it.id == id }
    }
}
