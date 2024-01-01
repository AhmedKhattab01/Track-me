package com.example.trackme.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Constants
import com.example.domain.models.task.Task
import com.example.domain.repo.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val TAG = this.javaClass.simpleName

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _sortResult = MutableStateFlow(0)
    val sortResult = _sortResult.asStateFlow()

    init {
        observeSort()
    }

    private fun observeSort() = viewModelScope.launch {
        sortResult.collectLatest  {
            when (it) {
                Constants.DEFAULT -> {
                    tasksRepository.getAllTasks().collect {
                        _tasks.value = it
                    }
                }

                Constants.ASCENDING -> {
                    tasksRepository.getAllTaskAsc().collect {
                        _tasks.value = it
                    }
                }

                Constants.DESCENDING -> {
                    tasksRepository.getAllTasksDesc().collect {
                        _tasks.value = it
                    }
                }
            }
        }
    }

    fun setSort(it: Int) {
        _sortResult.value = it
    }
}