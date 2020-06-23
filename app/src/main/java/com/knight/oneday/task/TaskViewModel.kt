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

    private val _taskList: MutableLiveData<List<Task>> = MutableLiveData()
    var taskList: LiveData<List<Task>> = _taskList

    private val _taskMonthScheme: MutableLiveData<MutableMap<String, UiCalendar>> =
        MutableLiveData()
    val taskMonthScheme: LiveData<MutableMap<String, UiCalendar>> = _taskMonthScheme

    val previewDateContent: String = currentWeekDayMonth()

    private var isChangeMonth: Boolean = true

    fun refreshTaskList() {
        launchOnIO(
            tryBlock = {
                taskRep.searchTaskByDay(dayCurrent.dayStartTime(), dayCurrent.dayEndTime()).run {
                    _taskList.postValue(this)
                }
                if (isChangeMonth) obtainCalendarScheme()
            }
        )
    }

    /**
     * 转换备注日历中的scheme
     */
    suspend fun obtainCalendarScheme() {
        val monthStartDay = dayCurrent.monthFirstDay()
        val monthEndDay = dayCurrent.monthEndDay()
        taskRep.searchTaskByDay(monthStartDay, monthEndDay).run {
            if (isNotEmpty()) {
                val map = mutableMapOf<String, UiCalendar>()
                forEach { task ->
                    val year = task.dueDateTime.get(Calendar.YEAR)
                    val month = task.dueDateTime.get(Calendar.MONTH) + 1
                    val day = task.dueDateTime.get(Calendar.DAY_OF_MONTH)

                    val key = UiCalendar()

                    key.year = task.dueDateTime.get(Calendar.YEAR)
                    key.month = task.dueDateTime.get(Calendar.MONTH) + 1
                    key.day = task.dueDateTime.get(Calendar.DAY_OF_MONTH)

                    if (map.containsKey(key.toString())) {
                        val uiCalendar = map[key.toString()]
                        uiCalendar?.let {
                            it.year = year
                            it.month = month
                            it.day = day
                            when {
                                task.isDone -> {
                                    if (!it.schemes.any { it.type == UI_CALENDAR_SCHEME_IS_DONE }) {
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
                                    if (!it.schemes.any { it.type == UI_CALENDAR_SCHEME_IS_EXPIRED }) {
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
                                    if (!it.schemes.any { it.type == UI_CALENDAR_SCHEME_NOT_EXPIRED }) {
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
                        map.put(uiCalendar.toString(), uiCalendar)
                    }
                }
                _taskMonthScheme.postValue(map)
                isChangeMonth = false
            }
        }
    }

    private fun obtainUiCalendar(year: Int, month: Int, day: Int) {

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
        isChangeMonth = dayCurrent.get(Calendar.MONTH) == month
        dayCurrent.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }
        refreshTaskList()
    }

}