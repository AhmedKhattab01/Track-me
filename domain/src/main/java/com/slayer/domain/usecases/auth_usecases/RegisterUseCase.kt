package com.slayer.domain.usecases.auth_usecases

import com.slayer.domain.repo.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = authRepository.register(email, password)
}