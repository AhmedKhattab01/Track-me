package com.example.trackme.ui.core.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FilterUtils {
    private val _sort: MutableStateFlow<Int> = MutableStateFlow(0)
    val sort: StateFlow<Int> get() = _sort

    fun setSort(value: Int) {
        _sort.value = value
    }
}