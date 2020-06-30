package com.knight.oneday.category

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.knight.oneday.data.Task
import com.knight.oneday.task.TaskAdapter
import com.knight.oneday.task.TaskDiffUtilItemCallback

class CategoryTaskAdapter(private val taskEventListener: TaskAdapter.TaskEventListener) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffUtilItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}