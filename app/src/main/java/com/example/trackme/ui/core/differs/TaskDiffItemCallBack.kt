package com.example.trackme.ui.core.differs

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.Task

class TaskDiffItemCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem

    }
}