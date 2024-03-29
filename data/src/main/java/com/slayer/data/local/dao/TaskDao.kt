package com.slayer.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.slayer.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks_table")
    fun getAllTasks() : Flow<List<TaskEntity>>

//    @Query("SELECT * FROM tasks_table ORDER BY taskName DESC")
//    fun getAllTasksDesc() : Flow<List<TaskEntity>>
//    @Query("SELECT * FROM tasks_table ORDER BY taskName ASC")
//    fun getAllTasksAsc() : Flow<List<TaskEntity>>
//
//    @Query("SELECT * FROM tasks_table ORDER BY creationTimeStamp DESC")
//    fun getAllTasksNewest() : Flow<List<TaskEntity>>
//
//    @Query("SELECT * FROM tasks_table ORDER BY creationTimeStamp ASC")
//    fun getAllTasksOldest() : Flow<List<TaskEntity>>

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAllLists()

    @Update
    suspend fun updateList(taskList: TaskEntity)

    @Insert
    suspend fun insertList(taskList: TaskEntity)

    @Delete
    suspend fun deleteList(taskList: TaskEntity)
}