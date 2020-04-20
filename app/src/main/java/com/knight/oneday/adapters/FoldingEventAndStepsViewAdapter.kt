package com.knight.oneday.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventAndEventSteps
import com.knight.oneday.databinding.EventCellLayoutBinding
import com.knight.oneday.databinding.RvItemMiniEventBinding
import com.knight.oneday.utilities.singleClick
import kotlinx.android.synthetic.main.rv_item_mini_event.view.*

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 极简版事件Adapter
 */
class FoldingEventAndStepsViewAdapter :
    ListAdapter<EventAndEventSteps, FoldingEventAndStepsViewAdapter.EventCellViewHolder>(
        EventAndStepsDiffCallback()
    ) {

    private lateinit var binding: EventCellLayoutBinding

    private var onFoldingItemClickListener: OnFoldingItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCellViewHolder {
        binding = EventCellLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventCellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventCellViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class EventCellViewHolder(private val binding: EventCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EventAndEventSteps) {
            binding.apply {
                eventAndSteps = item
                /*// 决定了 突出得圆角(打算用来做已完成得处理)
                eventCard.progress = 1F*/
                // 设置预览部分
                with(includeOverview) {
                    eventContent = item.event.content
                    remindTime = "12:00"
                    overviewStepContent.bindStepsOverView(item.eventSteps)
                    overviewCard.progress = 1F
                }
                // 点击事件
                binding.includeOverview.overviewCard.singleClick {
                    Log.d("TAG", "click")
                    binding.foldingCell.toggle(false)
                }
                executePendingBindings()
            }
        }
    }

    interface OnFoldingItemClickListener {

        fun onOverviewItemClick()

        fun onContentItemClick()

    }

}

private class EventAndStepsDiffCallback : DiffUtil.ItemCallback<EventAndEventSteps>() {
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
                && oldItem.event.state == newItem.event.state
                && oldItem.event.type == newItem.event.type
                && oldItem.event.eventId == newItem.event.eventId
    }
}