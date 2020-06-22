package com.knight.oneday.task

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

    private val _taskList: MutableLiveData<List<Task>> = MutableLiveData()
    var taskList: LiveData<List<Task>> = _taskList

    private val _taskMonthScheme: MutableLiveData<MutableMap<String, UiCalendar>> =
        MutableLiveData()
    val taskMonthScheme: LiveData<MutableMap<String, UiCalendar>> = _taskMonthScheme

    val previewDateContent: String = currentWeekDayMonth()

    fun refreshTaskList() {
        launchOnIO(
            tryBlock = {
                taskRep.searchTaskByDay(dayCurrent.dayStartTime(), dayCurrent.dayEndTime()).run {
                    _taskList.postValue(this)
                }
                obtainCalendarScheme()
            }
        )
    }

    /**
     * 转换备注日历中的scheme
     */
    suspend fun obtainCalendarScheme() {
        val monthStartDay = dayCurrent.monthFirstDay()
        val monthEndDay = dayCurrent.dayEndTime()
        taskRep.searchTaskByDay(monthStartDay, monthEndDay).run {
            if (isNotEmpty()) {
                val map = mutableMapOf<String, UiCalendar>()
                forEach { task ->
                    val key = task.dueDateTime.get(Calendar.DAY_OF_MONTH).toString()

                    val uiCalendar = if (map.containsKey(key)) map[key] else UiCalendar()

                    uiCalendar?.run {
                        year = task.dueDateTime.get(Calendar.YEAR)
                        month = task.dueDateTime.get(Calendar.MONTH)
                        day = task.dueDateTime.get(Calendar.DAY_OF_MONTH)

                        when {
                            task.isDone -> {
                                if (schemes.indexOfFirst { it.type == UI_CALENDAR_SCHEME_IS_DONE } == -1) {
                                }
                                addScheme(
                                    UI_CALENDAR_SCHEME_IS_DONE,
                                    OneDayApp.instance().themeColor(R.attr.timeLineFinishedFillColor),
                                    task.content
                                )
                            }
                            task.isExpired() -> {
                                if (schemes.indexOfFirst { it.type == UI_CALENDAR_SCHEME_IS_EXPIRED } == -1) {
                                }
                                addScheme(
                                    UI_CALENDAR_SCHEME_IS_EXPIRED,
                                    OneDayApp.instance().themeColor(R.attr.timeLineExpiredFillColor),
                                    task.content
                                )


                            }
                            else -> {
                                if (schemes.indexOfFirst { it.type == UI_CALENDAR_SCHEME_NOT_EXPIRED } == -1) {
                                }
                                addScheme(
                                    UI_CALENDAR_SCHEME_NOT_EXPIRED,
                                    OneDayApp.instance().themeColor(R.attr.timeLineUnfinishedFillColor),
                                    task.content
                                )

                            }
                        }
                        map.put(key, this)
                    }
                }
                _taskMonthScheme.postValue(map)
            }
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
        dayCurrent.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }
        refreshTaskList()
    }

}