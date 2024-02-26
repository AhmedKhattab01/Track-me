package com.slayer.trackme.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slayer.domain.models.task.Task
import com.slayer.trackme.databinding.ItemRvListBinding

class TaskAdapter() : ListAdapter<Task, TaskAdapter.TaskListViewHolder>(TaskDiffer()) {
    inner class TaskListViewHolder(private val binding: ItemRvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.apply {
                tvTitle.text = item.title
                tvDueDate.text = item.dueDate.toString()

                btnPriority.text = item.priority.toString()

                cbCompleted.isChecked = item.isCompleted
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        return TaskListViewHolder(
            ItemRvListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}