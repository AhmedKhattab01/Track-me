package com.slayer.domain.usecases.tasks_usecases

import com.slayer.domain.models.task.Task
import com.slayer.domain.repo.TasksRepository

class UpdateTaskUseCase (private val tasksRepository: TasksRepository) {
    suspend operator fun invoke(task: Task) {
        tasksRepository.updateTask(task)
    }
}