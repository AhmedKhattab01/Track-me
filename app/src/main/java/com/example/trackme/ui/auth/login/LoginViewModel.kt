package com.example.trackme.ui.auth.login

import androidx.lifecycle.ViewModel
import com.example.domain.models.TaskResult
import com.example.domain.usecases.LoginUseCase
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginResult: MutableStateFlow<AuthResult?> = MutableStateFlow(null)
    val loginResult = _loginResult.asStateFlow()

    suspend fun tryLogin(email: String, password: String) {
        loginUseCase.login(email, password).apply {
            when (this) {
                is TaskResult.Failure -> _loginResult.value = null
                is TaskResult.Loading -> TODO()
                is TaskResult.Success -> _loginResult.value = this.value as AuthResult
            }
        }
    }
}