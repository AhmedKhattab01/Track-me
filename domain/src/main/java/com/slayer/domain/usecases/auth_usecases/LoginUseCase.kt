package com.slayer.domain.usecases.auth_usecases

import com.slayer.domain.repo.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = authRepository.login(email, password)
}