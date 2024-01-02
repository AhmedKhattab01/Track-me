package com.slayer.domain.repo

import com.slayer.domain.models.TaskResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email : String,password : String) : TaskResult<Any>

    suspend fun loginWithGoogle(token : String) : TaskResult<Any>

    fun logout() : Flow<TaskResult<Any>>
}