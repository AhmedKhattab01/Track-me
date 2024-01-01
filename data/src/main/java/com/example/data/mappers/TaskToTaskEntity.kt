package com.example.data.mappers

import com.example.data.local.entities.TaskEntity
import com.example.domain.DtoToDomain
import com.example.domain.models.task.Task

class TaskToTaskEntity : DtoToDomain<Task,  TaskEntity> {
    override fun map(from: Task): TaskEntity {
        return TaskEntity(
            id = from.id,
            taskName = from.taskName,
            creationTimeStamp = from.creationTimeStamp,
            taskColorHex = from.taskColorHex,
            totalSubTasks = from.totalSubTasks,
        )
    }
}
