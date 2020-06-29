package com.knight.oneday.task


import android.app.Activity
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
import com.knight.oneday.add.AddTaskFragmentArgs
import com.knight.oneday.calendar.CalendarToolView
import com.knight.oneday.data.Task
import com.knight.oneday.databinding.FragmentTaskBinding
import com.knight.oneday.setting.SettingPreferences
import com.knight.oneday.utilities.DIALOG_TAG_DELETE_EVENT
import com.knight.oneday.utilities.formatWeekMonthDay
import com.knight.oneday.views.SureDeleteDialog

/**
 * Create by FLJ in 2020/6/18 14:08
 * 处理一些Task UI 逻辑业务
 */
class TaskUiPresenter(
    private val vm: TaskViewModel,
    private val binding: FragmentTaskBinding,
    private val act: FragmentActivity,
    private val navController: NavController
) :
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

    override fun onTaskClicked(view: View, task: Task) {
        navController.navigate(TaskFragmentDirections.actionTaskFragmentToAddEventFragment(task))
    }

    override fun onTaskLongClicked(task: Task): Boolean {
        if (SettingPreferences.showRemindDelete) {
            SureDeleteDialog().apply {
                onSure = {
                    vm.deleteTask(task)
                }
            }.show(act.supportFragmentManager, DIALOG_TAG_DELETE_EVENT)
        } else {
            vm.deleteTask(task)
        }
        return true
    }

    override fun onTaskStatusChanged(task: Task, isDone: Boolean) {
        vm.changeTaskStatus(task, isDone)
    }
}