package com.slayer.trackme.ui.auth.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.slayer.common.Utils.printToLog
import com.slayer.domain.models.TaskResult
import com.slayer.domain.usecases.auth_usecases.GoogleAuthUseCase
import com.slayer.domain.usecases.auth_usecases.RegisterUseCase
import com.slayer.trackme.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val googleAuthUseCase: GoogleAuthUseCase
) : ViewModel() {
    private val _registerResult = MutableStateFlow<AuthResult?>(null)
    val registerResult = _registerResult.asStateFlow()

    private val _authErrorFlow = MutableStateFlow<Exception?>(null)
    private val authErrorFlow = _authErrorFlow.asStateFlow()

    suspend fun tryRegister(email: String, password: String) {
        registerUseCase(email, password).apply {
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

    suspend fun tryLoginWithGoogle(token : String) {
        googleAuthUseCase(token).apply {
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

    fun handleSignUpWithEmailAndPasswordException(): Int {
        return when (val exception = authErrorFlow.value) {
            is FirebaseAuthUserCollisionException -> {
                R.string.account_already_registered
            }

            else -> {
                exception?.stackTraceToString().printToLog()
                R.string.something_went_wrong_auth
            }
        }
    }
}