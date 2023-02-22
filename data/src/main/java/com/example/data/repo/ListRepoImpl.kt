package com.example.data.repo

import com.example.data.local.dao.ListDao
import com.example.domain.models.TaskList
import com.example.domain.repo.ListRepository
import kotlinx.coroutines.flow.Flow

class ListRepoImpl(private val listDao: ListDao) : ListRepository{
    override fun getAllLists(): Flow<List<TaskList>> = listDao.getAllLists()

    override suspend fun deleteAllLists() = listDao.deleteAllLists()

    override suspend fun updateList(taskList: TaskList) = listDao.updateList(taskList)

    override suspend fun insertList(taskList: TaskList) = listDao.insertList(taskList)

    override suspend fun deleteList(taskList: TaskList) = listDao.deleteList(taskList)
}