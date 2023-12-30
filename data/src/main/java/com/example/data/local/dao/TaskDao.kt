package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.domain.models.TaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM lists_table")
    fun getAllLists() : Flow<List<TaskList>>

    @Query("SELECT * FROM lists_table ORDER BY name DESC")
    fun getAllListsDesc() : Flow<List<TaskList>>
    @Query("SELECT * FROM lists_table ORDER BY name ASC")
    fun getAllListsAsc() : Flow<List<TaskList>>

    @Query("DELETE FROM lists_table")
    suspend fun deleteAllLists()

    @Update
    suspend fun updateList(taskList: TaskList)

    @Insert
    suspend fun insertList(taskList: TaskList)

    @Delete
    suspend fun deleteList(taskList: TaskList)
}