package com.slayer.domain.usecases.auth_usecases

import com.slayer.domain.repo.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend fun login(email: String, password: String) = authRepository.login(email, password)
    suspend fun loginWithGoogle(token : String) = authRepository.loginWithGoogle(token)
}