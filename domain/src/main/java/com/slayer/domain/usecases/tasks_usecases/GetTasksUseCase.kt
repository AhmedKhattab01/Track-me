package com.slayer.domain.usecases.tasks_usecases

import com.slayer.domain.repo.TasksRepository

class GetTasksUseCase (private val tasksRepository: TasksRepository) {
    fun getAllTasks() = tasksRepository.getAllTasks()
//    fun getAllTaskAsc() = tasksRepository.getAllTaskAsc()
//    fun getAllTasksDesc() = tasksRepository.getAllTasksDesc()
//    fun getAllTasksNewest() = tasksRepository.getAllTasksNewest()
//    fun getAllTasksOldest() = tasksRepository.getAllTasksOldest()
}