package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String = "",
    var isCompleted: Boolean = false,
    val creationDate: String,
    val expirationDate: String = "",
    val listType: String
)
