package com.example.trackme.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.TaskList
import com.example.domain.repo.ListRepository
import com.maltaisn.icondialog.pack.IconDrawableLoader
import com.maltaisn.icondialog.pack.IconPack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listRepository: ListRepository,
    private val iconPack: IconPack,
    private val drawableLoader: IconDrawableLoader
) : ViewModel() {

    var color = -16738680

    var iconId = 0
    val iconsPack = this.iconPack
    val drawableLoad = drawableLoader

    var list: Flow<List<TaskList>> = listRepository.getAllLists()

    private val _filter: MutableStateFlow<Int> = MutableStateFlow(0)
    val filter: StateFlow<Int> get() = _filter

    fun setFilter(value: Int) {
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