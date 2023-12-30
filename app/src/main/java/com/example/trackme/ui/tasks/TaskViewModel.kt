package com.example.trackme.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.TaskList
import com.example.domain.repo.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val tasksRepository: TasksRepository) : ViewModel() {

    var color = -16738680
    var list: Flow<List<TaskList>> = tasksRepository.getAllTasks()

    fun deleteAllLists() = viewModelScope.launch(Dispatchers.IO) {
        tasksRepository.deleteAllTasks()
    }

    fun insertList(taskList: TaskList) = viewModelScope.launch(Dispatchers.IO) {
        tasksRepository.insertTask(taskList)
    }

    fun deleteList(taskList: TaskList) = viewModelScope.launch(Dispatchers.IO) {
        tasksRepository.deleteTask(taskList)
    }

    fun updateList(taskList: TaskList) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTask(taskList)
        }
    }
}