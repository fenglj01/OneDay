package com.knight.oneday.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
class ExpandableHomeItemAdapter(private val recyclerView: RecyclerView) :
    ListAdapter<EventAndEventSteps, ExpandableHomeItemAdapter.EventCellViewHolder>(
        EventAndStepsDiffCallback
    ) {

    private val defaultExpandedItem = -1
    private var expandedItem: Int = defaultExpandedItem
    var insertItem: Int = -1

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

    override fun onCurrentListChanged(
        previousList: MutableList<EventAndEventSteps>,
        currentList: MutableList<EventAndEventSteps>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        if (currentList.size > previousList.size) {
            insertItem = currentList.indexOfFirst { !previousList.contains(it) }
        }
    }

    inner class EventCellViewHolder(private val binding: EventCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @Deprecated("支持仅单个展开功能 (取消) 取消的原因submit后会导致失效")
        private fun toggle(position: Int) {

            val expandedItemViewHolder =
                recyclerView.findViewHolderForAdapterPosition(expandedItem) as? EventCellViewHolder
            expandedItemViewHolder?.run {
                binding.expandOverview.collapse()
                Log.d("SingleChoice", "$expandedItem collapse")
            }

            val clickItemViewHolder =
                recyclerView.findViewHolderForAdapterPosition(position) as? EventCellViewHolder
            expandedItem = if (position == expandedItem) {
                defaultExpandedItem
            } else {
                Log.d("SingleChoice", "$position expand ${clickItemViewHolder == null}")
                clickItemViewHolder?.binding?.expandOverview?.expand()
                position
            }
        }

        fun bind(item: EventAndEventSteps) {

            binding.apply {
                eventAndSteps = item
                // 展开与否
                val isExpanded = adapterPosition == expandedItem
                binding.expandOverview.toggleNoAnimator(isExpanded)

                // 设置预览部分
                with(includeOverview) {
                    eventContent = item.event.content
                    remindTime = "14:00"
                    overviewCard.progress = 1F
                    if (item.eventSteps.isNotEmpty()) {
                        expandOverview.addExpandableStatusListener(expandButton)
                        overviewStepContent.bindStepsOverView(item.eventSteps)
                        expandButton.singleClick {
                            expandOverview.toggle()
                        }
                        expandButton.visibility = View.VISIBLE
                        expandOverview.visibility = View.VISIBLE
                    } else {
                        expandButton.visibility = View.GONE
                        expandOverview.visibility = View.GONE
                    }
                }
                // 设置内容部分
                with(includeContent) {
                    content = item
                    contentStepListControl.setUpStepList(item.eventSteps)
                    expandOverview.addExpandableStatusListener(ExpandableStatusListenerLambdaAdapter(
                        /*整个卡片的高度视差*/
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

object EventAndStepsDiffCallback : DiffUtil.ItemCallback<EventAndEventSteps>() {
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
        return oldItem == newItem
    }
}