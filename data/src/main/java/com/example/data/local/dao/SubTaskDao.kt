package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.domain.models.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface SubTaskDao {
    @Query("SELECT * FROM tasks_table WHERE listType = :listType ORDER BY isCompleted")
    fun getTasks(listType : String) : Flow<List<Task>>

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAllTasks()

    @Query("SELECT COUNT(isCompleted) FROM tasks_table WHERE listType = :listType AND isCompleted = 1")
    fun countCompleted(listType: String) : Int

    @Query("DELETE FROM tasks_table WHERE listType = :listType")
    fun deleteListTasks(listType: String)

    @Update
    suspend fun updateTask(task: Task)

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}