package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists_table")
data class TaskList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val creationDate: String,
    val expireDate: String,
    val completedTasks : Int = 0,
    val totalTasks : Int = 0,
    var color: Int,
    var iconId : Int
)
