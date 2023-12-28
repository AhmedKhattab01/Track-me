package com.example.trackme.ui.core.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Task
import com.example.domain.models.TaskList
import com.example.trackme.databinding.ItemRvTaskBinding
import com.example.trackme.ui.screens.todo.task.TaskViewModel
import com.example.trackme.ui.screens.todo.tasks_lists.ListViewModel
import com.example.trackme.ui.core.differs.TaskDiffItemCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAdapter(
    private val taskViewModel: TaskViewModel,
    private val listViewModel: ListViewModel,
    private val taskList: TaskList
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffItemCallBack()) {
    inner class TaskViewHolder(private val binding: ItemRvTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {

            with(binding) {
                checkBox.setOnCheckedChangeListener { _, b ->
                    item.isCompleted = b
                    taskViewModel.updateTask(item)

                    CoroutineScope(Dispatchers.IO).launch {
                        taskList.completedTasks = taskViewModel.countCompletedTasks(taskList.name)
                        listViewModel.updateList(taskList)
                    }

                    if (item.isCompleted) {
                        binding.checkBox.paintFlags = binding.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    else {
                        binding.checkBox.paintFlags = binding.checkBox.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
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