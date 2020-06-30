package com.knight.oneday.category

import com.knight.oneday.task.TaskAdapter
import com.knight.oneday.utilities.format24Hex
import com.knight.oneday.utilities.formatWeekMonthDay

/**
 * Create by FLJ in 2020/6/30 9:43
 * 分类任务适配上注意修改一下时间显示即可
 */
class CategoryTaskAdapter(private val taskEventListener: TaskAdapter.TaskEventListener) :
    TaskAdapter(taskEventListener) {

    override fun getTaskHour(position: Int): String {
        return getTask(position).dueDateTime.timeInMillis.format24Hex()
    }

}