package com.slayer.domain.models

sealed class TaskResult<out T> {
    data class Success<out R>(val value: R) : TaskResult<R>()
    data class Failure(
        val message: String?,
        val statusCode: Int?,
        val body: Any? = null,
    ) : TaskResult<Nothing>()

    class Loading<T> : TaskResult<T>()
}