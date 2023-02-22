package com.example.data.local.dao

import androidx.room.*
import com.example.domain.models.Task
import com.example.domain.models.TaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
    @Query("SELECT * FROM lists_table")
    fun getAllLists() : Flow<List<TaskList>>

    @Query("DELETE FROM lists_table")
    suspend fun deleteAllLists()

    @Update
    suspend fun updateList(taskList: TaskList)

    @Insert
    suspend fun insertList(taskList: TaskList)

    @Delete
    suspend fun deleteList(taskList: TaskList)
}