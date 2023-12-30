package com.example.trackme.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.common.Constants
import com.example.domain.models.TaskResult
import com.example.domain.usecases.LoginUseCase
import com.example.trackme.R
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private val _loginResult = MutableStateFlow<AuthResult?>(null)
    val loginResult = _loginResult.asStateFlow()

    private val _authErrorFlow = MutableStateFlow<Exception?>(null)
    private val authErrorFlow = _authErrorFlow.asStateFlow()

    suspend fun tryLogin(email: String, password: String) {
        loginUseCase.login(email, password).apply {
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

            else -> {
                Log.e(TAG, "handleSignInWithEmailAndPasswordException: ${exception?.stackTraceToString()}")
                R.string.something_went_wrong
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
                R.string.something_went_wrong
            }
        }
    }
}