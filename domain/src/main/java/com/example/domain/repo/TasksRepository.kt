package com.example.domain.repo

import com.example.domain.models.TaskList
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getAllTasks(): Flow<List<TaskList>>
    fun getAllTaskAsc(): Flow<List<TaskList>>
    fun getAllTasksDesc(): Flow<List<TaskList>>
    suspend fun deleteAllTasks()
    suspend fun updateTask(taskList: TaskList)
    suspend fun insertTask(taskList: TaskList)
    suspend fun deleteTask(taskList: TaskList)
}