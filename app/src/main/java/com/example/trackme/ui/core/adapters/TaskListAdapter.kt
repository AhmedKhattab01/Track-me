package com.example.trackme.ui.core.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.TaskList
import com.example.trackme.databinding.ItemRvListBinding
import com.example.trackme.ui.core.differs.ListDiffItemCallBack
import com.example.trackme.ui.screens.todo.tasks_lists.ListFragmentDirections
import com.example.trackme.ui.screens.todo.tasks_lists.ListViewModel
import com.example.trackme.ui.shared_viewmodels.IconViewModel

class TaskListAdapter(private val listViewModel: ListViewModel, private val iconViewModel: IconViewModel) :
    ListAdapter<TaskList, TaskListAdapter.TaskListViewHolder>(ListDiffItemCallBack()) {
    inner class TaskListViewHolder(private val binding: ItemRvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskList) {
            binding.list = item
            with(binding.ivIcon) {
                setImageDrawable(
                    iconViewModel.iconPack.getIconDrawable(
                        item.iconId,
                        iconViewModel.drawableLoader
                    )
                )

                setColorFilter(item.color)
            }
            binding.progressBar.progressTintList = ColorStateList.valueOf(item.color)
            binding.executePendingBindings()
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
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(ListFragmentDirections.actionListFragmentToTaskFragment(getItem(holder.layoutPosition)))
        }
    }
}