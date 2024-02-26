package com.slayer.data.mappers

import com.slayer.data.local.entities.TaskEntity
import com.slayer.domain.DtoToDomain
import com.slayer.domain.models.task.Task

class TaskToTaskEntity : DtoToDomain<Task,  TaskEntity> {
    override fun map(from: Task): TaskEntity {
        return TaskEntity(
            id = from.id,
            title = from.title,
            categoryID = from.categoryID,
            description = from.description,
            dueDate = from.dueDate,
            priority = from.priority,
            isCompleted = from.isCompleted
        )
    }
}
