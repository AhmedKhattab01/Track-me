package com.example.trackme.ui.utils.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Task
import com.example.trackme.databinding.ItemRvTaskBinding
import com.example.trackme.ui.screens.task.TaskViewModel
import com.example.trackme.ui.utils.differs.TaskDiffItemCallBack

class TaskAdapter(private val taskViewModel: TaskViewModel) : ListAdapter<Task,TaskAdapter.TaskViewHolder>(TaskDiffItemCallBack()) {
    inner class TaskViewHolder(private val binding: ItemRvTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.task = item

            binding.checkBox.setOnCheckedChangeListener { compoundButton, b ->
                item.isCompleted = b
                taskViewModel.updateTask(item)

                if (item.isCompleted) {
                    binding.checkBox.paintFlags = binding.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else {
                    binding.checkBox.paintFlags = binding.checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemRvTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}