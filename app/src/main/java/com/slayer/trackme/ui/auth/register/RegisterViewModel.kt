package com.slayer.trackme.ui.auth.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.slayer.domain.models.TaskResult
import com.slayer.domain.usecases.auth_usecases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _registerResult = MutableStateFlow<AuthResult?>(null)
    val registerResult = _registerResult.asStateFlow()

    private val _authErrorFlow = MutableStateFlow<Exception?>(null)
    private val authErrorFlow = _authErrorFlow.asStateFlow()

    suspend fun tryRegister(email: String, password: String) {
        registerUseCase.register(email, password).apply {
            when (this) {
                is TaskResult.Failure -> {
                    _registerResult.value = null
                    _authErrorFlow.value = this.body as Exception
                }

                is TaskResult.Loading -> {}
                is TaskResult.Success -> _registerResult.value = this.value as AuthResult
            }
        }
    }
}