package com.slayer.trackme.ui.auth.forget

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.slayer.common.Utils.printToLog
import com.slayer.domain.models.TaskResult
import com.slayer.domain.usecases.auth_usecases.ForgetPasswordUseCase
import com.slayer.trackme.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val forgetPasswordUseCase: ForgetPasswordUseCase
) : ViewModel() {
    private val _forgetPasswordResult = MutableStateFlow<Boolean?>(null)
    val forgetPasswordResult = _forgetPasswordResult.asStateFlow()

    private val _authErrorFlow = MutableStateFlow<Exception?>(null)
    private val authErrorFlow = _authErrorFlow.asStateFlow()

    suspend fun forgetPassword(email : String) = forgetPasswordUseCase(email).apply {
        when (this) {
            is TaskResult.Failure -> {
                _forgetPasswordResult.value = false
                _authErrorFlow.value = this.body as Exception
                _authErrorFlow.value.printToLog()
            }

            is TaskResult.Loading -> {}
            is TaskResult.Success -> _forgetPasswordResult.value = true
        }
    }

    fun handleForgetPasswordExceptions(): Int {
        return when (val exception = authErrorFlow.value) {
            is FirebaseAuthInvalidUserException -> {
                R.string.email_is_not_registered
            }

            else -> {
                exception?.stackTraceToString().printToLog()
                R.string.something_went_wrong_auth
            }
        }
    }
}