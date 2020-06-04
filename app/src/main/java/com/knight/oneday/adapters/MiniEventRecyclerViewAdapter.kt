package com.knight.oneday.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventAndEventSteps
import com.knight.oneday.databinding.RvItemMiniEventBinding
import com.knight.oneday.utilities.singleClick
import kotlinx.android.synthetic.main.rv_item_mini_event.view.*

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 极简版事件Adapter
 */
class MiniEventRecyclerViewAdapter :
    ListAdapter<EventAndEventSteps, MiniEventRecyclerViewAdapter.MiniEventViewHolder>(
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

        fun bind(item: EventAndEventSteps) {
            binding.apply {
                eventAndSteps = item
                // 决定了 突出得圆角(打算用来做已完成得处理)
                eventCard.progress = 1F
                executePendingBindings()
            }
        }
    }

}

private class EventDiffCallback : DiffUtil.ItemCallback<EventAndEventSteps>() {
    override fun areItemsTheSame(
        oldItem: EventAndEventSteps,
        newItem: EventAndEventSteps
    ): Boolean {
        return oldItem.event.eventId == newItem.event.eventId
    }

    override fun areContentsTheSame(
        oldItem: EventAndEventSteps,
        newItem: EventAndEventSteps
    ): Boolean {
        return oldItem.event.content == newItem.event.content
                && oldItem.event.isDone == newItem.event.isDone
                && oldItem.event.eventId == newItem.event.eventId
    }
}