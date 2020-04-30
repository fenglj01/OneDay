package com.knight.oneday.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.EventAndEventSteps
import com.knight.oneday.databinding.EventCellLayoutBinding
import com.knight.oneday.utilities.dp2px
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.expand.ExpandableLayout
import com.knight.oneday.views.expand.ExpandableStatusListenerLambdaAdapter

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 首页可展开的ListAdapter
 */
class ExpandableHomeItemAdapter :
    ListAdapter<EventAndEventSteps, ExpandableHomeItemAdapter.EventCellViewHolder>(
        EventAndStepsDiffCallback()
    ) {
    /* 暂时采用了记录变量的方式 */
    private var currentExpanded: ExpandableLayout? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCellViewHolder {
        return EventCellViewHolder(
            EventCellLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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
                // 设置预览部分
                with(includeOverview) {
                    eventContent = item.event.content
                    remindTime = "14:00"
                    overviewStepContent.bindStepsOverView(item.eventSteps)
                    overviewCard.progress = 1F
                    expandOverview.addExpandableStatusListener(expandButton)
                    expandButton.singleClick {
                        expandOverview.toggle()
                    }
                }
                // 设置内容部分
                with(includeContent) {
                    content = item
                    contentStepListControl.setUpStepList(item.eventSteps)
                    expandOverview.addExpandableStatusListener(ExpandableStatusListenerLambdaAdapter(
                        onPreExpand = {
                            if (expandOverview != currentExpanded) {
                                currentExpanded?.toggle()
                                currentExpanded = expandOverview
                            }
                        },
                        onExpanded = {
                        },
                        onCollapsed = {
                        },
                        onFraction = { fraction, isExpanding ->
                            val f = if (isExpanding) fraction else 1F - fraction
                            val z = dp2px(dp = 8F) * f
                            includeOverview.overviewContent.translationZ = z
                            expandOverview.translationZ = z
                        }

                    ))
                }
                executePendingBindings()
            }
        }
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