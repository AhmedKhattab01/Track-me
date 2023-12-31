package com.example.trackme.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.task.Task
import com.example.domain.repo.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val tasksRepository: TasksRepository) : ViewModel() {

    var list: Flow<List<Task>> = tasksRepository.getAllTasks()

    fun deleteAllLists() = viewModelScope.launch(Dispatchers.IO) {
        tasksRepository.deleteAllTasks()
    }

    fun insertList(taskList: Task) = viewModelScope.launch(Dispatchers.IO) {
        tasksRepository.insertTask(taskList)
    }

    fun deleteList(taskList: Task) = viewModelScope.launch(Dispatchers.IO) {
        tasksRepository.deleteTask(taskList)
    }

    fun updateList(taskList: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTask(taskList)
        }
    }
}