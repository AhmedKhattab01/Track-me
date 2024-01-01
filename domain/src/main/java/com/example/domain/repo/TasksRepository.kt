package com.example.domain.repo

import com.example.domain.models.task.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getAllTaskAsc(): Flow<List<Task>>
    fun getAllTasksDesc(): Flow<List<Task>>
    suspend fun deleteAllTasks()
    suspend fun updateTask(task: Task)
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
}