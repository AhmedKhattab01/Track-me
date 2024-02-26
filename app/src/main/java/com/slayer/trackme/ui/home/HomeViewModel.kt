package com.slayer.trackme.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slayer.common.Constants
import com.slayer.domain.models.task.Task
import com.slayer.domain.usecases.tasks_usecases.DeleteTaskUseCase
import com.slayer.domain.usecases.tasks_usecases.GetTasksUseCase
import com.slayer.domain.usecases.tasks_usecases.InsertTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
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
                    getTasksUseCase.getAllTasks().collect {
                        _tasks.value = it
                    }
                }

//                Constants.ASCENDING -> {
//                    getTasksUseCase.getAllTaskAsc().collect {
//                        _tasks.value = it
//                    }
//                }
//
//                Constants.DESCENDING -> {
//                    getTasksUseCase.getAllTasksDesc().collect {
//                        _tasks.value = it
//                    }
//                }
//
//                Constants.OLDEST -> {
//                    getTasksUseCase.getAllTasksOldest().collect {
//                        _tasks.value = it
//                    }
//                }
//
//                Constants.NEWEST -> {
//                    getTasksUseCase.getAllTasksNewest().collect {
//                        _tasks.value = it
//                    }
//                }
            }
        }
    }

    fun setSort(it: Int) {
        _sortResult.value = it
    }

    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        deleteTaskUseCase.invoke(task)
    }

    fun insertTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        insertTaskUseCase.invoke(task)
    }
}