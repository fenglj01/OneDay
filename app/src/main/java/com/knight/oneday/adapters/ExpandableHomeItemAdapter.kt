package com.knight.oneday.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventAndEventSteps
import com.knight.oneday.data.Step
import com.knight.oneday.databinding.EventWithStepItemLayoutBinding
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.dp2px
import com.knight.oneday.utilities.formatUi
import com.knight.oneday.utilities.singleClick
import com.knight.oneday.views.OnButtonClickListener
import com.knight.oneday.views.expand.ExpandableStatusListenerLambdaAdapter
import com.knight.oneday.views.hsv.OnStepIndicatorClickListener
import com.knight.oneday.views.swipe.EventSwipeActionDrawable
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback
import kotlin.math.abs

/**
 * @author knight
 * create at 20-3-11 下午8:36
 * 首页可展开的ListAdapter
 */
class ExpandableHomeItemAdapter(
    val eventItemListener: EventItemListener,
    val recyclerView: RecyclerView
) :
    ListAdapter<EventAndEventSteps, ExpandableHomeItemAdapter.EventCellViewHolder>(
        EventAndStepsDiffCallback
    ) {

    var currentChangedItemIndex: Int = -1
    private val expandedItemList: MutableSet<Int> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCellViewHolder {
        return EventCellViewHolder(
            EventWithStepItemLayoutBinding.inflate(
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

    inner class EventCellViewHolder(private val binding: EventWithStepItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), ReboundingSwipeActionCallback.ReboundableViewHolder {

        init {
            binding.root.background = EventSwipeActionDrawable(binding.root.context)
        }

        fun bind(item: EventAndEventSteps) {

            binding.apply {

                /* 修改步骤预览展开相关的可见性 */
                fun changeStepViewVisible(isVisible: Boolean) {
                    expandButton.isVisible = isVisible
                    stepContentExpand.isVisible = isVisible
                    stepOverviewTv.isVisible = isVisible
                }

                /* 修改事件相关内容字体颜色 根据是否完成 */
                fun changeTextColor(isDone: Boolean) {
                    val alpha = if (isDone) 0.6F else 1F
                    remindTimeIv.alpha = alpha
                    remindTimeTv.alpha = alpha
                    overviewEventContent.alpha = alpha
                }

                /* 基本内容初始化 */
                eventAndSteps = item
                binding.root.isActivated = item.event.isDone
                eventCard.progress = if (item.event.isDone) 1F else 0F

                /* 如果一完成那么不需要显示步骤预览以及可展开了 */
                changeStepViewVisible(!item.event.isDone)
                changeTextColor(item.event.isDone)
                // 设置预览部分
                eventContent = item.event.content
                remindTime = item.event.dueDateTime.formatUi()

                // 点击 长按事件
                eventCard.singleClick {
                    eventItemListener.onEventClicked(item.event)
                }
                eventCard.setOnLongClickListener {
                    eventItemListener.onEventLongPressed(item.event)
                    true
                }
                /* 如果用户已经完成了这项任务 那么不需要做内容的适配了 */
                if (!item.event.isDone) {
                    // 展开step内容相关
                    val isExpanded = expandedItemList.contains(adapterPosition)
                    stepContentExpand.toggleNoAnimator(isExpanded)
                    /* 步骤是否为空的情况决定预览和站看内容的可见性 */
                    if (item.eventSteps.isNotEmpty()) {
                        stepContentExpand.addExpandableStatusListener(expandButton)
                        stepOverviewTv.bindStepsOverView(item.eventSteps)
                        expandButton.singleClick {
                            stepContentExpand.toggle()
                        }
                        changeStepViewVisible(true)
                    } else {
                        changeStepViewVisible(false)
                    }
                    // 设置内容部分
                    with(includeContent) {
                        content = item
                        if (item.eventSteps.isNotEmpty()) {
                            /* 步骤指示器点击事件 */
                            hsv.onStepIndicatorClickListener =
                                object : OnStepIndicatorClickListener {
                                    override fun onStepIndicatorClick(pos: Int) {
                                        val currentStep = item.eventSteps[pos]
                                        tvStep.text = currentStep.content
                                        /* 对按钮的操作 */
                                        stepDoneUndoIb.setImageState(
                                            if (currentStep.isDone()) intArrayOf(android.R.attr.state_activated) else intArrayOf(
                                                -android.R.attr.state_activated
                                            ), true
                                        )
                                    }
                                }
                            stepDoneUndoIb.onButtonClickListener = object : OnButtonClickListener {
                                override fun onStateOnForwardClick() {
                                    stepDoneUndoIb.revert()
                                    val selectIndex = hsv.selectStepNumber() - 1
                                    if (selectIndex in item.eventSteps.indices) {
                                        eventItemListener.onStepIbClicked(
                                            item.eventSteps[selectIndex],
                                            true
                                        )
                                    }

                                }

                                override fun onStateOnReserveClick() {
                                    stepDoneUndoIb.revert()
                                    val selectIndex = hsv.selectStepNumber()
                                    if (selectIndex in item.eventSteps.indices) {
                                        eventItemListener.onStepIbClicked(
                                            item.eventSteps[selectIndex],
                                            false
                                        )
                                    }
                                }
                            }
                            hsv.bindStepIndicator(item.eventSteps)
                            /* 获取当前正在执行项的位置 */
                            val currentIndex =
                                item.eventSteps.indexOfFirst { it.state == EventState.UNFINISHED }
                            tvStep.text =
                                if (currentIndex != -1) item.eventSteps[currentIndex].content else item.eventSteps.first().content
                            /* 展开内容的监听 */
                            stepContentExpand.addExpandableStatusListener(
                                ExpandableStatusListenerLambdaAdapter(
                                    onExpanded = {
                                        expandedItemList.add(adapterPosition)
                                        /* 滚动到当前项 */
                                        hsv.smoothScrollToStep(item.eventSteps.indexOfFirst { it.state == EventState.UNFINISHED } + 1)
                                    },
                                    onCollapsed = {
                                        expandedItemList.remove(adapterPosition)
                                    }/*,
                                为了实现滑动完成任务 只能暂时关闭这个功能了
                                *//*整个卡片的高度视差*//*
                                onFraction = { fraction, isExpanding ->
                                    recyclerView.clipChildren = false
                                    val f = if (isExpanding) fraction else 1F - fraction
                                    val z = dp2px(dp = 8F) * f
                                    eventCard.translationZ = z
                                }*/
                                )
                            )
                        }
                    }
                }
                executePendingBindings()
            }
        }

        override val reboundableView: View
            get() = binding.eventCard

        override fun onReboundOffsetChanged(
            currentSwipePercentage: Float,
            swipeThreshold: Float,
            currentTargetHasMetThresholdOnce: Boolean
        ) {
            if (currentTargetHasMetThresholdOnce) return
            val isDone = binding.eventAndSteps?.event?.isDone ?: false

            // Animate the top left corner radius of the email card as swipe happens.
            val interpolation = (currentSwipePercentage / swipeThreshold).coerceIn(0F, 1F)
            val adjustedInterpolation = abs((if (isDone) 1F else 0F) - interpolation)
            binding.eventCard.progress = adjustedInterpolation

            // Start the background animation once the threshold is met.
            val thresholdMet = currentSwipePercentage >= swipeThreshold
            val shouldStar = when {
                thresholdMet && isDone -> false
                thresholdMet && !isDone -> true
                else -> return
            }
            binding.root.isActivated = shouldStar
        }

        override fun onRebounded() {
            val event = binding.eventAndSteps?.event ?: return
            eventItemListener.onEventDoneChanged(event, !event.isDone)
        }
    }

    interface EventItemListener {
        fun onEventDoneChanged(event: Event, isDone: Boolean)
        fun onEventLongPressed(event: Event)
        fun onEventClicked(event: Event)
        fun onStepIbClicked(step: Step, isDone: Boolean)
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