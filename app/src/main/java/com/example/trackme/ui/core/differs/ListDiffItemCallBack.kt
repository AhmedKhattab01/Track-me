package com.example.trackme.ui.core.differs

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.TaskList

class ListDiffItemCallBack : DiffUtil.ItemCallback<TaskList>() {
    override fun areItemsTheSame(oldItem: TaskList, newItem: TaskList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskList, newItem: TaskList): Boolean {
        return oldItem == newItem
    }
}