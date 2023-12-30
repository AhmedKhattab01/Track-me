package com.example.data.repo

import com.example.data.local.dao.TaskDao
import com.example.domain.models.TaskList
import com.example.domain.repo.TasksRepository
import kotlinx.coroutines.flow.Flow

class TasksRepoImpl(private val taskDao: TaskDao) : TasksRepository{
    override fun getAllTasks(): Flow<List<TaskList>> = taskDao.getAllLists()
    override fun getAllTaskAsc(): Flow<List<TaskList>> = taskDao.getAllListsAsc()

    override fun getAllTasksDesc(): Flow<List<TaskList>> = taskDao.getAllListsDesc()
    override suspend fun deleteAllTasks() = taskDao.deleteAllLists()

    override suspend fun updateTask(taskList: TaskList) = taskDao.updateList(taskList)

    override suspend fun insertTask(taskList: TaskList) = taskDao.insertList(taskList)

    override suspend fun deleteTask(taskList: TaskList) = taskDao.deleteList(taskList)
}