package com.knight.oneday.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.TaskAndEventSteps
import com.knight.oneday.databinding.RvItemMiniEventBinding

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 极简版事件Adapter
 */
class MiniEventRecyclerViewAdapter :
    ListAdapter<TaskAndEventSteps, MiniEventRecyclerViewAdapter.MiniEventViewHolder>(
        EventDiffCallback()
    ) {

    private lateinit var binding: RvItemMiniEventBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniEventViewHolder {
        binding = RvItemMiniEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MiniEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MiniEventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class MiniEventViewHolder(private val binding: RvItemMiniEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskAndEventSteps) {
            binding.apply {
                eventAndSteps = item
                // 决定了 突出得圆角(打算用来做已完成得处理)
                eventCard.progress = 1F
                executePendingBindings()
            }
        }
    }

}

private class EventDiffCallback : DiffUtil.ItemCallback<TaskAndEventSteps>() {
    override fun areItemsTheSame(
        oldItem: TaskAndEventSteps,
        newItem: TaskAndEventSteps
    ): Boolean {
        return oldItem.task.eventId == newItem.task.eventId
    }

    override fun areContentsTheSame(
        oldItem: TaskAndEventSteps,
        newItem: TaskAndEventSteps
    ): Boolean {
        return oldItem.task.content == newItem.task.content
                && oldItem.task.isDone == newItem.task.isDone
                && oldItem.task.eventId == newItem.task.eventId
    }
}