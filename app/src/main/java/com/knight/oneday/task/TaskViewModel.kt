package com.knight.oneday.task

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.OneDayApp
import com.knight.oneday.R
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskRepository
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.BaseViewModel
import com.knight.oneday.views.themeColor
import java.util.*

/**
 * Create by FLJ in 2020/6/16 9:37
 * 新版主页ViewModel
 */
class TaskViewModel(private val taskRep: TaskRepository) : BaseViewModel() {

    var dayCurrent = currentCalendar()
    private var _taskMonthList: List<Task> = listOf()
    /* 任务清单(天为单位) */
    private val _taskList: MutableLiveData<List<Task>> = MutableLiveData()
    var taskList: LiveData<List<Task>> = _taskList
    /* Calendar 任务提示(月为单位) */
    private val _taskMonthScheme: MutableLiveData<MutableMap<String, UiCalendar>> =
        MutableLiveData()
    val taskMonthScheme: LiveData<MutableMap<String, UiCalendar>> = _taskMonthScheme

    val previewDateContent: String = currentWeekDayMonth()

    fun refreshTaskList() {
        launchOnIO(
            tryBlock = {
                val monthStartDay = dayCurrent.monthFirstDay()
                val monthEndDay = dayCurrent.monthEndDay()
                taskRep.searchTaskByDay(monthStartDay, monthEndDay).run {
                    _taskMonthList = this
                    if (isNotEmpty()) {
                        task2CalendarScheme()
                    }
                    filterTaskByCurrentDay()
                }
            }
        )
    }

    /* 转换CalendarScheme 只有在月份切换 以及创建了新任务 删除了任务的时候会触发刷新 */
    private fun task2CalendarScheme() {
        val map = mutableMapOf<String, UiCalendar>()
        _taskMonthList.forEach { task ->
            val year = task.dueDateTime.get(Calendar.YEAR)
            val month = task.dueDateTime.get(Calendar.MONTH) + 1
            val day = task.dueDateTime.get(Calendar.DAY_OF_MONTH)

            val key = UiCalendar()

            key.year = year
            key.month = month
            key.day = day

            if (map.containsKey(key.toString())) {
                val uiCalendar = map[key.toString()]
                uiCalendar?.let {
                    it.year = year
                    it.month = month
                    it.day = day
                    when {
                        task.isDone -> {
                            if (!it.schemes.any { scheme -> scheme.type == UI_CALENDAR_SCHEME_IS_DONE }) {
                                it.addScheme(
                                    UiScheme(
                                        UI_CALENDAR_SCHEME_IS_DONE,
                                        themeColor(R.attr.colorFinished),
                                        task.content
                                    )
                                )
                            }
                        }
                        task.isExpired() -> {
                            if (!it.schemes.any { scheme -> scheme.type == UI_CALENDAR_SCHEME_IS_EXPIRED }) {
                                it.addScheme(
                                    UiScheme(
                                        UI_CALENDAR_SCHEME_IS_EXPIRED,
                                        themeColor(R.attr.colorExpired),
                                        task.content
                                    )
                                )
                            }
                        }
                        else -> {
                            if (!it.schemes.any { scheme -> scheme.type == UI_CALENDAR_SCHEME_NOT_EXPIRED }) {
                                it.addScheme(
                                    UiScheme(
                                        UI_CALENDAR_SCHEME_NOT_EXPIRED,
                                        themeColor(R.attr.colorUnFinished),
                                        task.content
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                val uiCalendar = UiCalendar()
                uiCalendar.let {
                    it.year = year
                    it.month = month
                    it.day = day
                    it.addScheme(
                        UiScheme(
                            when {
                                task.isExpired() -> UI_CALENDAR_SCHEME_IS_EXPIRED
                                task.isDone -> UI_CALENDAR_SCHEME_IS_DONE
                                else -> UI_CALENDAR_SCHEME_NOT_EXPIRED
                            },
                            themeColor(
                                when {
                                    task.isExpired() -> R.attr.colorExpired
                                    task.isDone -> R.attr.colorFinished
                                    else -> R.attr.colorUnFinished
                                }
                            ),
                            task.content
                        )
                    )
                }
                map[uiCalendar.toString()] = uiCalendar
            }
        }
        _taskMonthScheme.postValue(map)
    }

    /* 筛选出当前日期的taskList */
    private fun filterTaskByCurrentDay() {
        val currentStartTime = dayCurrent.dayStartTime().timeInMillis
        val currentEndTime = dayCurrent.dayEndTime().timeInMillis
        _taskMonthList.asSequence().filter { task ->
            task.dueDateTime.timeInMillis in (currentStartTime..currentEndTime)
        }.toList().run {
            _taskList.postValue(this)
        }
    }

    fun changeTaskStatus(task: Task, isDone: Boolean) {
        launchOnIO(
            tryBlock = {
                taskRep.updateEventDoneStatus(task.taskId, isDone).run {
                    refreshTaskList()
                }
            }
        )
    }

    fun deleteTask(task: Task) {
        launchOnIO(
            tryBlock = {
                taskRep.deleteTask(task.taskId).run {
                    refreshTaskList()
                }
            }
        )
    }

    fun changeTaskDay(year: Int, month: Int, day: Int) {
        val isChangeMonth = dayCurrent.get(Calendar.MONTH) == month
        dayCurrent.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }
        TaskFragment.selectDate.postValue(dayCurrent)
        /* 如果是修改了月份 那么全部刷新 如果只是修改了日期那么查询一下即可 */
        if (isChangeMonth) refreshTaskList() else filterTaskByCurrentDay()
    }

}