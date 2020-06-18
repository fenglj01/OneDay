package com.knight.oneday.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskAndStepRepository
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.BaseViewModel
import java.util.*

/**
 * Create by FLJ in 2020/6/15 15:09
 * 添加
 */
class AddTaskViewModel(private val rep: TaskAndStepRepository) : BaseViewModel() {

    companion object {
        const val ADD_STATUS_CONTENT_IS_EMPTY = 0
        const val ADD_STATUS_FAIL = 1
        const val ADD_STATUS_SUCCESS = 2
    }

    val previewDateContent: String = currentWeekDayMonth()
    val previewTimeContent: String = currentHourMin()

    var taskContent: String = ""
    var taskDueDateTime: Calendar = GregorianCalendar.getInstance()
    var taskType: TaskType = TaskType.NO_CATEGORY

    private val _addTaskStatus: MutableLiveData<Int> = MutableLiveData()
    val addTaskStatus: LiveData<Int> = _addTaskStatus

    fun prepareHourMinStr(hourOfDay: Int, minute: Int) =
        "${if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"}:${if (minute < 10) "0$minute" else "$minute"}"


    fun addTask() {
        if (!checkCanAdd()) {
            _addTaskStatus.postValue(ADD_STATUS_CONTENT_IS_EMPTY)
            return
        }
        launchOnIO(
            tryBlock = {
                rep.createEvent(
                    task = Task(
                        content = taskContent,
                        dueDateTime = taskDueDateTime,
                        taskType = taskType
                    )
                ).run {
                    _addTaskStatus.postValue(ADD_STATUS_SUCCESS)
                }
            },
            catchBlock = {
                _addTaskStatus.postValue(ADD_STATUS_FAIL)
            }
        )
    }

    private fun checkCanAdd(): Boolean = taskContent.isNotEmpty()

    fun changeDate(year: Int, month: Int, day: Int) {
        taskDueDateTime.run {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }

    }

    fun changeHourAndMinute(hourOfDay: Int, minute: Int) {
        taskDueDateTime.run {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
    }

    fun changeEventType(selectIndex: Int) {
        taskType = TaskType.values().first { it.ordinal == selectIndex }
    }
}