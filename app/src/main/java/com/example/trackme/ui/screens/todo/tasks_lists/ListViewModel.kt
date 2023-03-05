package com.example.trackme.ui.screens.todo.tasks_lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Task
import com.example.domain.models.TaskList
import com.example.domain.repo.ListRepository
import com.example.trackme.ui.core.utils.FilterUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val listRepository: ListRepository) : ViewModel() {

    var color = -16738680
    var list: Flow<List<TaskList>> = listRepository.getAllLists()

    val currentFilter: StateFlow<Int> get() = FilterUtils.sort

    fun deleteAllLists() = viewModelScope.launch(Dispatchers.IO) {
        listRepository.deleteAllLists()
    }

    fun insertList(taskList: TaskList) = viewModelScope.launch(Dispatchers.IO) {
        listRepository.insertList(taskList)
    }

    fun deleteList(taskList: TaskList) = viewModelScope.launch(Dispatchers.IO) {
        listRepository.deleteList(taskList)
    }

    fun updateList(taskList: TaskList) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.updateList(taskList)
        }
    }

    fun sortTasks(tasks: List<TaskList>): List<TaskList> {
        return when (currentFilter.value) {
            1 -> tasks.sortedBy { it.name } // Ascending
            2 -> tasks.sortedByDescending { it.name } // Descending
            else -> tasks
        }
    }
}