package com.knight.oneday.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.ItemTaskLayoutBinding
import com.knight.oneday.utilities.currentTimeMills
import com.knight.oneday.utilities.format24Hex
import com.knight.oneday.utilities.formatMonthDay
import com.knight.oneday.utilities.getHourAndMin
import com.knight.oneday.views.swipe.EventSwipeActionDrawable
import com.knight.oneday.views.swipe.ReboundingSwipeActionCallback
import kotlin.math.abs

class TaskAdapter(private val taskEventListener: TaskEventListener) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffUtilItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position), taskEventListener)
    }

    class TaskViewHolder(private val binding: ItemTaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), ReboundingSwipeActionCallback.ReboundableViewHolder {

        init {
            binding.root.background = EventSwipeActionDrawable(binding.root.context)
        }

        fun bind(task: Task, taskEventListener: TaskEventListener) {
            binding.task = task
            binding.taskEventListener = taskEventListener
            binding.root.isActivated = task.isDone
            binding.taskItemCard.progress = if (task.isDone) 1F else 0F
        }

        override val reboundableView: View
            get() = binding.taskItemCard

        override fun onReboundOffsetChanged(
            currentSwipePercentage: Float,
            swipeThreshold: Float,
            currentTargetHasMetThresholdOnce: Boolean
        ) {
            if (currentTargetHasMetThresholdOnce) return
            val isDone = binding.task?.isDone ?: false

            // Animate the top left corner radius of the email card as swipe happens.
            val interpolation = (currentSwipePercentage / swipeThreshold).coerceIn(0F, 1F)
            val adjustedInterpolation = abs((if (isDone) 1F else 0F) - interpolation)
            binding.taskItemCard.progress = adjustedInterpolation

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
            val task = binding.task ?: return
            binding.taskEventListener?.onTaskStatusChanged(task, !task.isDone)
        }
    }

    fun getTaskListIndices() = currentList.indices

    fun getTask(position: Int) = getItem(position)

    fun getTaskHour(position: Int, isDay: Boolean) =
        if (isDay) getTask(position).dueDateTime.getHourAndMin()
        else getTask(position).dueDateTime.timeInMillis.formatMonthDay()

    fun getTaskType(position: Int) = getTask(position).taskType

    fun getTaskStatus(position: Int): Int {
        val task = getTask(position)
        if (task.isDone) return TaskTimeLineItemDecoration.STATUS_FINISHED
        if (task.dueDateTime.timeInMillis < currentTimeMills()) return TaskTimeLineItemDecoration.STATUS_EXPIRED
        return TaskTimeLineItemDecoration.STATUS_UNFINISHED
    }

    interface TaskEventListener {

        fun onTaskClicked(view: View, task: Task)

        fun onTaskLongClicked(task: Task): Boolean

        fun onTaskStatusChanged(task: Task, isDone: Boolean)

    }


}

object TaskDiffUtilItemCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}