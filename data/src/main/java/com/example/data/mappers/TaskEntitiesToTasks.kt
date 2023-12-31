package com.example.data.mappers

import com.example.data.local.entities.TaskEntity
import com.example.domain.DtoToDomain
import com.example.domain.models.task.Task

class TaskEntitiesToTasks : DtoToDomain<List<TaskEntity>, List<Task>> {
    override fun map(from: List<TaskEntity>): List<Task> {
        return from.map {
            Task(
                id = it.id,
                taskName = it.taskName,
                creationTimeStamp = it.creationTimeStamp,
                taskColorHex = it.taskColorHex,
                totalSubTasks = it.totalSubTasks,
            )
        }
    }
}