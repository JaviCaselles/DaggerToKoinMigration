package com.example.poc.feature.c.domain

import com.example.poc.data.model.Product
import com.example.poc.data.model.User
import com.example.poc.feature.a.domain.GetUsersUseCase
import com.example.poc.feature.b.domain.GetProductsUseCase

class AggregatedUseCase(
    private val getUsersUseCase: GetUsersUseCase,
    private val getProductsUseCase: GetProductsUseCase
) {
    suspend operator fun invoke(): Pair<List<User>, List<Product>> {
        val users = getUsersUseCase()
        val products = getProductsUseCase()
        return users to products
    }
}
