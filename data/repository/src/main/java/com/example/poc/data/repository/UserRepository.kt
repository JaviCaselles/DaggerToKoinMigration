package com.example.poc.data.repository

import com.example.poc.data.api.ApiService
import com.example.poc.data.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: String): User?
}

internal class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return try {
            apiService.getUsers()
        } catch (e: Exception) {
            // Datos mock para el POC
            listOf(
                User("1", "John Doe", "john@example.com"),
                User("2", "Jane Smith", "jane@example.com"),
                User("3", "Bob Wilson", "bob@example.com")
            )
        }
    }

    override suspend fun getUser(id: String): User? {
        return getUsers().find { it.id == id }
    }
}
