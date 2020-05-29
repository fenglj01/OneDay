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
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.dp2px
import com.knight.oneday.utilities.formatUi
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.expand.ExpandableStatusListenerLambdaAdapter
import com.knight.oneday.views.hsv.OnStepIndicatorClickListener

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 首页可展开的ListAdapter
 */
class ExpandableHomeItemAdapter(private val recyclerView: RecyclerView) :
    ListAdapter<EventAndEventSteps, ExpandableHomeItemAdapter.EventCellViewHolder>(
        EventAndStepsDiffCallback
    ) {

    var currentChangedItemIndex: Int = -1
    private val expandedItemList: MutableSet<Int> = mutableSetOf()

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
        if (previousList.isNullOrEmpty()) return
        when {
            currentList.size > previousList.size -> {       // insert
                currentChangedItemIndex = currentList.indexOfFirst { !previousList.contains(it) }
            }
            currentList.size < previousList.size -> {       // delete
                currentChangedItemIndex = previousList.indexOfFirst { !currentList.contains(it) }
            }
            else -> {                                       // update or nothing
                currentList.forEachIndexed { index, eventAndEventSteps ->
                    if (previousList[index] != eventAndEventSteps) {
                        currentChangedItemIndex = index
                        return@forEachIndexed
                    }
                }
            }
        }
    }

    fun refreshCurrentChangeItemIndex() {
        currentChangedItemIndex = -1
    }

    inner class EventCellViewHolder(private val binding: EventCellLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EventAndEventSteps) {

            binding.apply {
                eventAndSteps = item
                // 展开与否
                val isExpanded = expandedItemList.contains(adapterPosition)
                binding.expandOverview.toggleNoAnimator(isExpanded)
                // 设置预览部分
                with(includeOverview) {
                    eventContent = item.event.content
                    remindTime = item.event.reminderTime.formatUi()
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
                    hsv.onStepIndicatorClickListener = object : OnStepIndicatorClickListener {
                        override fun onStepIndicatorClick(pos: Int) {
                            tvStep.text = item.eventSteps[pos].content
                        }
                    }
                    val currentIndex =
                        item.eventSteps.indexOfFirst { it.state == EventState.UNFINISHED }
                    tvStep.text =
                        if (currentIndex != -1) item.eventSteps[currentIndex].content else item.eventSteps.first().content
                    expandOverview.addExpandableStatusListener(ExpandableStatusListenerLambdaAdapter(
                        onExpanded = {
                            expandedItemList.add(adapterPosition)
                        },
                        onCollapsed = {
                            expandedItemList.remove(adapterPosition)
                        },
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