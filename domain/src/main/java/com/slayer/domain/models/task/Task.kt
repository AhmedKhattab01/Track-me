package com.slayer.domain.models.task


data class Task(
    val id : Int,
    val title : String,
    val description : String,
    val categoryID : Int,
    val dueDate : Long,
    val priority : Int,
    val isCompleted : Boolean
)
