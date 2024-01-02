package com.slayer.domain.repo

import com.slayer.domain.models.task.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getAllTaskAsc(): Flow<List<Task>>
    fun getAllTasksDesc(): Flow<List<Task>>
    fun getAllTasksNewest(): Flow<List<Task>>
    fun getAllTasksOldest(): Flow<List<Task>>
    suspend fun deleteAllTasks()
    suspend fun updateTask(task: Task)
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
}