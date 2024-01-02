package com.slayer.data.repo

import com.slayer.data.local.dao.TaskDao
import com.slayer.data.local.entities.TaskEntity
import com.slayer.domain.DtoToDomain
import com.slayer.domain.models.task.Task
import com.slayer.domain.repo.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksRepoImpl(
    private val taskDao: TaskDao,
    private val tasksEntitiesToTask: DtoToDomain<List<TaskEntity>, List<Task>>,
    private val taskToTaskEntity: DtoToDomain<Task, TaskEntity>,
) : TasksRepository {
    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { tasksEntitiesToTask.map(it) }

    override fun getAllTaskAsc(): Flow<List<Task>> =
        taskDao.getAllTasksAsc().map { tasksEntitiesToTask.map(it) }

    override fun getAllTasksDesc(): Flow<List<Task>> =
        taskDao.getAllTasksDesc().map { tasksEntitiesToTask.map(it) }

    override fun getAllTasksNewest(): Flow<List<Task>> =
        taskDao.getAllTasksNewest().map { tasksEntitiesToTask.map(it) }

    override fun getAllTasksOldest(): Flow<List<Task>> =
        taskDao.getAllTasksOldest().map { tasksEntitiesToTask.map(it) }

    override suspend fun deleteAllTasks() = taskDao.deleteAllLists()

    override suspend fun updateTask(task: Task) = taskDao.updateList(taskToTaskEntity.map(task))

    override suspend fun insertTask(task: Task) = taskDao.insertList(taskToTaskEntity.map(task))

    override suspend fun deleteTask(task: Task) = taskDao.deleteList(taskToTaskEntity.map(task))
}