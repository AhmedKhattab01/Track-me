package com.example.data.local.dao

import androidx.room.*
import com.example.domain.models.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks_table")
    fun getAllTasks() : Flow<List<Task>>

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAllTasks()

    @Update
    suspend fun updateTask(task: Task)

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}