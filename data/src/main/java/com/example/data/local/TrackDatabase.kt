package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.TaskDao
import com.example.data.local.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}