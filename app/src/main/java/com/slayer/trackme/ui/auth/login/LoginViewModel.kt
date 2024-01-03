package com.slayer.trackme.ui.auth.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.slayer.common.Constants
import com.slayer.common.Utils.printToLog
import com.slayer.domain.models.TaskResult
import com.slayer.domain.usecases.auth_usecases.GoogleAuthUseCase
import com.slayer.domain.usecases.auth_usecases.LoginUseCase
import com.slayer.trackme.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val googleAuthUseCase: GoogleAuthUseCase
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private val _loginResult = MutableStateFlow<AuthResult?>(null)
    val loginResult = _loginResult.asStateFlow()

    private val _authErrorFlow = MutableStateFlow<Exception?>(null)
    private val authErrorFlow = _authErrorFlow.asStateFlow()

    suspend fun tryLogin(email: String, password: String) {
        loginUseCase(email, password).apply {
            when (this) {
                is TaskResult.Failure -> {
                    _loginResult.value = null
                    _authErrorFlow.value = this.body as Exception
                }

                is TaskResult.Loading -> {}
                is TaskResult.Success -> _loginResult.value = this.value as AuthResult
            }
        }
    }

    suspend fun tryLoginWithGoogle(token : String) {
        googleAuthUseCase(token).apply {
            when (this) {
                is TaskResult.Failure -> {
                    _loginResult.value = null
                    _authErrorFlow.value = this.body as Exception
                }

                is TaskResult.Loading -> {}
                is TaskResult.Success -> _loginResult.value = this.value as AuthResult
            }
        }
    }

    fun handleSignInWithEmailAndPasswordException(): Int {
        return when (val exception = authErrorFlow.value) {
            is FirebaseAuthInvalidUserException -> {
                handleInvalidUser(exception)
            }

            is FirebaseAuthInvalidCredentialsException -> {
                R.string.incorrect_password
            }

            else -> {
                exception?.stackTraceToString().printToLog()
                R.string.something_went_wrong_auth
            }
        }
    }

    private fun handleInvalidUser(exception: FirebaseAuthInvalidUserException): Int {
        return when (exception.errorCode) {
            Constants.USER_NOT_FOUND -> {
                R.string.user_not_found
            }

            Constants.USER_DISABLED -> {
                R.string.user_disabled
            }

            Constants.USER_TOKEN_EXPIRED -> {
                R.string.user_token_expired
            }

            Constants.INVALID_USER_TOKEN -> {
                R.string.invalid_user_token
            }

            else -> {
                exception.stackTraceToString().printToLog()
                R.string.something_went_wrong_auth
            }
        }
    }
}