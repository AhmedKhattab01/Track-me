package com.slayer.domain.usecases.auth_usecases

import com.slayer.domain.repo.AuthRepository

class GoogleAuthUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(token : String) = authRepository.loginWithGoogle(token)
}