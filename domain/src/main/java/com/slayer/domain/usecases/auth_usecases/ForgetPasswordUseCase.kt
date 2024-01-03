package com.slayer.domain.usecases.auth_usecases

import com.slayer.domain.repo.AuthRepository

class ForgetPasswordUseCase (private val authRepository: AuthRepository) {
    suspend operator fun invoke(email : String) = authRepository.forgetPassword(email)
}