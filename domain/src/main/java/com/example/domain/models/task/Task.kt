package com.example.domain.models.task


data class Task(
    val id : Int = 0,
    val taskName : String,
    val taskColorHex : String = "#000000",
    val creationTimeStamp : Long = System.currentTimeMillis(),
    val totalSubTasks : Int = 0,
    val taskIconId : Int? = null
)
