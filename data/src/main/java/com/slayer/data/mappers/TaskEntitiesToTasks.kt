package com.slayer.data.mappers

import com.slayer.data.local.entities.TaskEntity
import com.slayer.domain.DtoToDomain
import com.slayer.domain.models.task.Task

class TaskEntitiesToTasks : DtoToDomain<List<TaskEntity>, List<Task>> {
    override fun map(from: List<TaskEntity>): List<Task> {
        return from.map {
            Task(
                id = it.id,
                title = it.title,
                categoryID = it.categoryID,
                description = it.description,
                dueDate = it.dueDate,
                priority = it.priority,
                isCompleted = it.isCompleted
            )
        }
    }
}