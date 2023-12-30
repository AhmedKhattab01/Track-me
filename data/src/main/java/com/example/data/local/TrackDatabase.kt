package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.TaskDao
import com.example.data.local.dao.SubTaskDao
import com.example.domain.models.Task
import com.example.domain.models.TaskList

@Database(entities = [TaskList::class, Task::class], version = 1, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun getListDao(): TaskDao
    abstract fun getTaskDao(): SubTaskDao
}