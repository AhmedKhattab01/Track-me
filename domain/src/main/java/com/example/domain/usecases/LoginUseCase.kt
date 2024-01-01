package com.example.domain.usecases

import com.example.domain.repo.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend fun login(email: String, password: String) = authRepository.login(email, password)
    suspend fun loginWithGoogle(token : String) = authRepository.loginWithGoogle(token)
}