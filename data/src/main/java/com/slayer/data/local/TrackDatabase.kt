package com.slayer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slayer.data.local.dao.TaskDao
import com.slayer.data.local.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TrackDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}