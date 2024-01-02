package com.slayer.domain.usecases.tasks_usecases

import com.slayer.domain.models.task.Task
import com.slayer.domain.repo.TasksRepository

class InsertTaskUseCase (private val tasksRepository: TasksRepository) {
    suspend operator fun invoke(task: Task) {
        tasksRepository.insertTask(task)
    }
}