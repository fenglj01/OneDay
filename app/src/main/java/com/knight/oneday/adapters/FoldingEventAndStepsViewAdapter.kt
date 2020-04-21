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

    private val unfoldedIndexes = hashSetOf<Int>()

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
            /* 根据当前展开的项 合理的展示折叠Item */
            if (unfoldedIndexes.contains(adapterPosition)) {
                binding.foldingCell.unfold(true)
            } else {
                binding.foldingCell.fold(true)
            }

            binding.apply {
                eventAndSteps = item
                /*// 决定了 突出得圆角(打算用来做已完成得处理 这个效果有待商榷)
                eventCard.progress = 1F*/
                // 设置预览部分
                with(includeOverview) {
                    eventContent = item.event.content
                    remindTime = "12:00"
                    overviewStepContent.bindStepsOverView(item.eventSteps)
                    overviewCard.progress = 1F
                }
                // 设置内容部分
                with(includeContent) {
                    content = item
                }
                // 预览视图下点击事件
                binding.includeOverview.overviewCard.singleClick {
                    // 拥有步骤的情况下 才有必要展开折叠的部分
                    if (item.haveSteps()) {
                        binding.foldingCell.toggle(false)
                        registerToggle(position = adapterPosition)
                    } else {
                        onFoldingItemClickListener?.onOverviewItemClick()
                    }

                }
                binding.includeContent.contentCard.singleClick {
                    binding.foldingCell.toggle(false)
                    registerToggle(position = adapterPosition)
                }
                executePendingBindings()
            }
        }
    }

    /*
        根据状态的切换 同步到记录中来 达到切换的效果
    */
    private fun registerToggle(position: Int) {
        if (unfoldedIndexes.contains(position))
            registerFold(position)
        else
            registerUnFold(position)
    }

    private fun registerFold(position: Int) {
        unfoldedIndexes.remove(position)
    }

    private fun registerUnFold(position: Int) {
        unfoldedIndexes.add(position)
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