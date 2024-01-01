package com.example.trackme.ui.home.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Constants
import com.example.domain.models.task.Task
import com.example.domain.repo.TasksRepository
import com.example.trackme.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val tasksRepository: TasksRepository) : ViewModel() {
    private var selectedColorHexCode: String = Constants.DEFAULT_TASK_COLOR

    private var selectedIconId: Int = R.drawable.baseline_star_border_24

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