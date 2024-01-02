package com.slayer.trackme.ui.home.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slayer.common.Constants
import com.slayer.domain.models.task.Task
import com.slayer.domain.repo.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val tasksRepository: TasksRepository) : ViewModel() {
    private var selectedColorHexCode: String = Constants.DEFAULT_TASK_COLOR

    private var selectedIconId: Int? = null

    fun setSelectedColorHexCode(value: String) {
        selectedColorHexCode = value
    }

    fun setSelectedIconId(value: Int) {
        selectedIconId = value
    }

    fun insertList(taskName : String) = viewModelScope.launch(Dispatchers.IO) {
        val task = Task(taskName = taskName, taskColorHex = selectedColorHexCode, taskIconId = selectedIconId)
        tasksRepository.insertTask(task)
    }
}