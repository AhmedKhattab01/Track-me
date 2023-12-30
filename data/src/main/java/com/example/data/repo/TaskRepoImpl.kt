package com.example.data.repo

import com.example.data.local.dao.SubTaskDao
import com.example.domain.models.Task
import com.example.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepoImpl(private val subTaskDao: SubTaskDao) : TaskRepository {
    override fun getAllTasks(listType : String): Flow<List<Task>> = subTaskDao.getTasks(listType)
    override suspend fun deleteListTasks(listType: String) = subTaskDao.deleteListTasks(listType)
    override fun countCompleted(listType: String): Int = subTaskDao.countCompleted(listType)
    override suspend fun deleteAllTasks() = subTaskDao.deleteAllTasks()
    override suspend fun updateTask(task: Task) = subTaskDao.updateTask(task)
    override suspend fun insertTask(task: Task) = subTaskDao.insertTask(task)
    override suspend fun deleteTask(task: Task) = subTaskDao.deleteTask(task)
}