package com.example.trackme.ui.screens.todo.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Task
import com.example.domain.repo.TaskRepository
import com.example.trackme.ui.core.utils.FilterUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    val currentFilter: StateFlow<Int> get() = FilterUtils.sort
    fun getTasks(listType : String) : Flow<List<Task>> = taskRepository.getAllTasks(listType)
    suspend fun countCompletedTasks(listType: String) : Int {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            taskRepository.countCompleted(listType)
        }
    }

    fun insertTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.insertTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.deleteTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.updateTask(task)
    }

    fun deleteListTasks(listType: String) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.deleteListTasks(listType)
    }
}