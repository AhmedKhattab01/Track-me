package com.example.trackme.ui.home.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.TaskList
import com.example.trackme.databinding.ItemRvListBinding

class TaskAdapter : ListAdapter<TaskList, TaskAdapter.TaskListViewHolder>(
    TaskDiffer()
) {
    inner class TaskListViewHolder(private val binding: ItemRvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskList) {
            binding.apply {
                tvName.text = item.name
                tvDate.text = item.creationDate

                progressBar.progressTintList = ColorStateList.valueOf(item.color)
            }

            with(binding.ivIcon) {
                setColorFilter(item.color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        return TaskListViewHolder(
            ItemRvListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}