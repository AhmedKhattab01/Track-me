package com.slayer.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks_table")
data class TaskEntity(
    @PrimaryKey(true)
    val id : Int,
    val title : String,
    val description : String,
    val categoryID : Int,
    val dueDate : Long,
    val priority : Int,
    val isCompleted : Boolean
)