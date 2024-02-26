package com.slayer.trackme.ui.home.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slayer.domain.models.task.Task
import com.slayer.domain.repo.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val tasksRepository: TasksRepository) : ViewModel() {
    fun insertList(taskName : String) = viewModelScope.launch(Dispatchers.IO) {
        val task = Task(id = 0, title = taskName, description = "Test", categoryID = 0, dueDate = 1L, priority = 1,isCompleted = false)
        tasksRepository.insertTask(task)
    }
}