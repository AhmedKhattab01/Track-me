package com.slayer.data.mappers

import com.slayer.data.local.entities.TaskEntity
import com.slayer.domain.DtoToDomain
import com.slayer.domain.models.task.Task

class TaskEntitiesToTasks : DtoToDomain<List<TaskEntity>, List<Task>> {
    override fun map(from: List<TaskEntity>): List<Task> {
        return from.map {
            Task(
                id = it.id,
                taskName = it.taskName,
                creationTimeStamp = it.creationTimeStamp,
                taskColorHex = it.taskColorHex,
                totalSubTasks = it.totalSubTasks,
                taskIconId = it.taskIconId
            )
        }
    }
}