package com.knight.oneday.task


import android.util.Log
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.calendar.CalendarToolView
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.FragmentTaskBinding
import com.knight.oneday.utilities.formatWeekMonthDay

/**
 * Create by FLJ in 2020/6/18 14:08
 * 处理一些Task UI 逻辑业务
 */
class TaskUiPresenter(private val vm: TaskViewModel, private val binding: FragmentTaskBinding) :
    TaskAdapter.TaskEventListener, CalendarView.OnCalendarSelectListener {

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        binding.taskCurrentDate.changeSelectedDay(
            calendar?.timeInMillis?.formatWeekMonthDay() ?: ""
        )
        calendar?.run {
            vm.changeTaskDay(year, month, day)
        }
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
    }

    override fun onTaskClicked(task: Task) {
        Log.d("task", "click $task")
    }

    override fun onTaskLongClicked(task: Task): Boolean {
        Log.d("task", "longClick $task")
        return true
    }

    override fun onTaskStatusChanged(task: Task, isDone: Boolean) {
    }
}