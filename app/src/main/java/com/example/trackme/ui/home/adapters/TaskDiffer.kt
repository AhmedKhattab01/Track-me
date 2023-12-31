package com.example.trackme.ui.home.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.task.Task

class TaskDiffer : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}