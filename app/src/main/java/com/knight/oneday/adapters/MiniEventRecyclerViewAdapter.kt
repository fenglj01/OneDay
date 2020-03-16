package com.knight.oneday.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Event
import com.knight.oneday.databinding.RvItemMiniEventBinding

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 极简版事件Adapter
 */
class MiniEventRecyclerViewAdapter :
    ListAdapter<Event, MiniEventRecyclerViewAdapter.MiniEventViewHolder>(EventDiffCallback()) {

    private lateinit var binding: RvItemMiniEventBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniEventViewHolder {
        binding = RvItemMiniEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MiniEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MiniEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getEvent(position: Int) = getItem(position)

    class MiniEventViewHolder(private val binding: RvItemMiniEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Event) {
            binding.apply {
                event = item
                executePendingBindings()
            }
        }
    }

}

private class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.content == newItem.content
    }
}