package com.example.domain.repo

import com.example.domain.models.TaskResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email : String,password : String) : TaskResult<Any>

    fun logout() : Flow<TaskResult<Any>>
}