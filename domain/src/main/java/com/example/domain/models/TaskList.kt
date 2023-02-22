package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists_table")
data class TaskList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val creationDate: String,
    var isFavourite: Boolean,
    var color: Int,
)
