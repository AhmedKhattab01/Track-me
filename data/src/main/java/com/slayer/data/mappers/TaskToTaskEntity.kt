package com.slayer.data.mappers

import com.slayer.data.local.entities.TaskEntity
import com.slayer.domain.DtoToDomain
import com.slayer.domain.models.task.Task

class TaskToTaskEntity : DtoToDomain<Task,  TaskEntity> {
    override fun map(from: Task): TaskEntity {
        return TaskEntity(
            id = from.id,
            taskName = from.taskName,
            creationTimeStamp = from.creationTimeStamp,
            taskColorHex = from.taskColorHex,
            totalSubTasks = from.totalSubTasks,
            taskIconId = from.taskIconId
        )
    }
}
