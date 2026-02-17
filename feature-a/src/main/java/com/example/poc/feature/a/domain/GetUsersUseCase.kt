package com.example.poc.feature.a.domain

import com.example.poc.data.model.User
import com.example.poc.data.repository.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return userRepository.getUsers()
    }
}
