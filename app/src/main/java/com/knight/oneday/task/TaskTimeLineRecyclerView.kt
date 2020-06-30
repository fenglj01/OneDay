package com.knight.oneday.task

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R
import com.knight.oneday.adapters.BaseTimeLineRecyclerView
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.utilities.dp
import com.knight.oneday.utilities.sp

/**
 * Create by FLJ in 2020/6/17 13:30
 */
class TaskTimeLineRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseTimeLineRecyclerView(context, attrs, defStyleAttr) {



    override fun getTime(position: Int): String {
        return (adapter as TaskAdapter).getTaskHour(position)
    }

    override fun getTaskType(position: Int): TaskType {
        return (adapter as TaskAdapter).getTaskType(position)
    }

    override fun getTaskStatus(position: Int): Int {
        return (adapter as TaskAdapter).getTaskStatus(position)
    }
}