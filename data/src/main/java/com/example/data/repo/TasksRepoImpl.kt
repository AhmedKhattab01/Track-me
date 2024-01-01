package com.example.data.repo

import com.example.data.local.dao.TaskDao
import com.example.data.local.entities.TaskEntity
import com.example.domain.DtoToDomain
import com.example.domain.models.task.Task
import com.example.domain.repo.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksRepoImpl(
    private val taskDao: TaskDao,
    private val tasksEntitiesToTask : DtoToDomain<List<TaskEntity>, List<Task>>,
    private val taskToTaskEntity : DtoToDomain<Task, TaskEntity>,
) : TasksRepository{
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllLists().map { tasksEntitiesToTask.map(it) }
    override fun getAllTaskAsc(): Flow<List<Task>> = taskDao.getAllListsAsc().map { tasksEntitiesToTask.map(it) }
    override fun getAllTasksDesc(): Flow<List<Task>> = taskDao.getAllListsDesc().map { tasksEntitiesToTask.map(it) }
    override suspend fun deleteAllTasks() = taskDao.deleteAllLists()

    override suspend fun updateTask(task: Task) = taskDao.updateList(taskToTaskEntity.map(task))

    override suspend fun insertTask(task: Task) = taskDao.insertList(taskToTaskEntity.map(task))

    override suspend fun deleteTask(task: Task) = taskDao.deleteList(taskToTaskEntity.map(task))
}