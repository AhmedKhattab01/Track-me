package com.example.domain.models.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(true)
    val id : Int,
    val taskName : String,
    val taskColorHex : String? = "#000000",
    val creationTimeStamp : Long? = System.currentTimeMillis(),
    val totalSubTasks : Int? = 0,
)
