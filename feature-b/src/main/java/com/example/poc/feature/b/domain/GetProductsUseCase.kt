package com.example.poc.feature.b.domain

import com.example.poc.data.model.Product
import com.example.poc.data.repository.ProductRepository

class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return productRepository.getProducts()
    }
}
