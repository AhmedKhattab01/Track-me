package com.example.trackme.ui.home.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.common.Utils
import com.example.common_ui.Utils.invisibleIf
import com.example.domain.models.task.Task
import com.example.trackme.databinding.ItemRvListBinding
import com.maltaisn.icondialog.pack.IconDrawableLoader
import com.maltaisn.icondialog.pack.IconPack

class TaskAdapter(private val drawableLoader: IconDrawableLoader,private val iconPack: IconPack) : ListAdapter<Task, TaskAdapter.TaskListViewHolder>(TaskDiffer()) {
    inner class TaskListViewHolder(private val binding: ItemRvListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.apply {
                tvName.text = item.taskName
                tvDate.text = Utils.convertTimestampToFriendlyDate(item.creationTimeStamp)

                ivIcon.setColorFilter(Color.parseColor(item.taskColorHex))

                item.taskIconId?.let {
                    ivIcon.setImageDrawable(
                        iconPack.getIconDrawable(it, drawableLoader)
                    )
                }

                progressBar.progressTintList =
                    ColorStateList.valueOf(Color.parseColor(item.taskColorHex))

                progressBar.invisibleIf(item.totalSubTasks <= 0)
                tvProgress.invisibleIf(item.totalSubTasks <= 0)
                tvCompletedTasks.invisibleIf(item.totalSubTasks <= 0)
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