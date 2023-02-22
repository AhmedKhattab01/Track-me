package com.example.trackme.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
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

            if (item.isFavourite) {
                binding.btnFavourite.setImageResource(R.drawable.baseline_star_24)
            }

            binding.btnFavourite.setOnClickListener {
                item.isFavourite = !item.isFavourite
                listViewModel.updateList(item)
                if (item.isFavourite) {
                    binding.btnFavourite.setImageResource(R.drawable.baseline_star_24)
                }
                else {
                    binding.btnFavourite.setImageResource(R.drawable.baseline_star_outline_24)
                }
            }

            binding.vColor.setBackgroundColor(item.color)

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
    }
}