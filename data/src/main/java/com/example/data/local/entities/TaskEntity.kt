package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks_table")
data class TaskEntity(
    @PrimaryKey(true)
    val id : Int,
    val taskName : String,
    val taskColorHex : String = "#000000",
    val creationTimeStamp : Long = System.currentTimeMillis(),
    val totalSubTasks : Int = 0,
    val taskIconId : Int,
)