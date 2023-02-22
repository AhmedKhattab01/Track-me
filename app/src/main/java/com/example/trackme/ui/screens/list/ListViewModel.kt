package com.example.trackme.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.TaskList
import com.example.domain.repo.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val listRepository: ListRepository) : ViewModel() {

    var isFavourite = false // Used in adding new list item to add it as favourite while created
    var color = -16738680

    var list : Flow<List<TaskList>> = listRepository.getAllLists()

    private val _filter :  MutableStateFlow<Int> = MutableStateFlow(0)
    val filter :  StateFlow<Int> get() = _filter

    fun setFilter(value : Int) {
        _filter.value = value
    }

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
}