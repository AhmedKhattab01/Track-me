package com.example.trackme.ui.utils.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.TaskList
import com.example.trackme.R
import com.example.trackme.databinding.ItemRvListBinding
import com.example.trackme.ui.screens.list.ListViewModel
import com.example.trackme.ui.utils.differs.ListDiffItemCallBack

class TaskListAdapter(private val listViewModel: ListViewModel) :
    ListAdapter<TaskList, TaskListAdapter.TaskListViewHolder>(ListDiffItemCallBack()) {
    inner class TaskListViewHolder(private val binding: ItemRvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskList) {
            binding.list = item
            with(binding.ivIcon) {
                setImageDrawable(
                    listViewModel.iconsPack.getIconDrawable(
                        item.iconId,
                        listViewModel.drawableLoad
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
            it.findNavController().navigate(R.id.action_listFragment_to_taskFragment)
        }
    }
}