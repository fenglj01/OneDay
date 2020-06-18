package com.knight.oneday.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.adapters.EventAndStepsDiffCallback
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.ItemTaskLayoutBinding
import com.knight.oneday.utilities.currentTimeMills
import com.knight.oneday.utilities.getHourAndMin
import com.knight.oneday.views.step.STEP_STATE_UNFINISHED

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
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, taskEventListener: TaskEventListener) {
            binding.task = task
            binding.taskEventListener = taskEventListener
        }

    }

    fun getTask(position: Int) = getItem(position)

    fun getTaskHour(position: Int) = getTask(position).dueDateTime.getHourAndMin()

    fun getTaskType(position: Int) = getTask(position).taskType

    fun getTaskStatus(position: Int): Int {
        val task = getTask(position)
        if (task.isDone) return TaskTimeLineItemDecoration.STATUS_FINISHED
        if (task.dueDateTime.timeInMillis < currentTimeMills()) return TaskTimeLineItemDecoration.STATUS_EXPIRED
        return STEP_STATE_UNFINISHED
    }

    interface TaskEventListener {

        fun onTaskClicked(task: Task)

        fun onTaskLongClicked(task: Task): Boolean

        fun onTaskStatusChanged(task: Task, isDone: Boolean)

    }


}

object TaskDiffUtilItemCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}