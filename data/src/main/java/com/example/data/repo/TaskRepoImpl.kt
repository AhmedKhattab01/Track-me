package com.example.data.repo

import com.example.data.local.dao.TaskDao
import com.example.domain.models.Task
import com.example.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepoImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun getAllTasks(listType : String): Flow<List<Task>> = taskDao.getTasks(listType)
    override suspend fun deleteListTasks(listType: String) = taskDao.deleteListTasks(listType)
    override fun countCompleted(listType: String): Int = taskDao.countCompleted(listType)
    override suspend fun deleteAllTasks() = taskDao.deleteAllTasks()
    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    override suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}