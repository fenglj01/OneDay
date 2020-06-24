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

        const val EDIT_STATUS_CONTENT_IS_EMPTY = 3
        const val EDIT_STATUS_FAIL = 4
        const val EDIT_STATUS_SUCCESS = 5
        const val EDIT_STATUS_NOTHING_CHANGED = 6
    }

    val previewDateContent: String = currentWeekDayMonth()
    val previewTimeContent: String = currentHourMin()

    var vmTaskContent: String = ""
    var vmTaskDueDateTime: Calendar = GregorianCalendar.getInstance()
    var vmTaskType: TaskType = TaskType.NO_CATEGORY

    private val _viewModelStatus: MutableLiveData<Int> = MutableLiveData()
    val viewModelStatus: LiveData<Int> = _viewModelStatus

    private var isAddTask = true
    private var updateTask: Task? = null

    fun prepareHourMinStr(hourOfDay: Int, minute: Int) =
        "${if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"}:${if (minute < 10) "0$minute" else "$minute"}"

    fun confirm() {
        if (isAddTask) addTask() else updateTask()
    }

    private fun updateTask() {
        if (!checkCanAdd()) {
            _viewModelStatus.postValue(EDIT_STATUS_CONTENT_IS_EMPTY)
            return
        }
        if (isNothingChanged() || updateTask == null) {
            _viewModelStatus.postValue(EDIT_STATUS_NOTHING_CHANGED)
            return
        }
        launchOnIO(
            tryBlock = {
                rep.updateEvent(updateTask!!.apply {
                    content = vmTaskContent
                    dueDateTime = vmTaskDueDateTime
                    taskType = vmTaskType
                }).run {
                    _viewModelStatus.postValue(EDIT_STATUS_SUCCESS)
                }
            },
            catchBlock = {
                _viewModelStatus.postValue(EDIT_STATUS_FAIL)
            }
        )
    }

    private fun addTask() {
        if (!checkCanAdd()) {
            _viewModelStatus.postValue(ADD_STATUS_CONTENT_IS_EMPTY)
            return
        }
        launchOnIO(
            tryBlock = {
                rep.createEvent(
                    task = Task(
                        content = vmTaskContent,
                        dueDateTime = vmTaskDueDateTime,
                        taskType = vmTaskType
                    )
                ).run {
                    _viewModelStatus.postValue(ADD_STATUS_SUCCESS)
                }
            },
            catchBlock = {
                _viewModelStatus.postValue(ADD_STATUS_FAIL)
            }
        )
    }

    private fun checkCanAdd(): Boolean = vmTaskContent.isNotEmpty()

    private fun isNothingChanged(): Boolean {
        return vmTaskContent == updateTask?.content &&
                vmTaskDueDateTime.timeInMillis == updateTask?.dueDateTime?.timeInMillis &&
                vmTaskType == updateTask?.taskType
    }

    fun changeDate(year: Int, month: Int, day: Int) {
        vmTaskDueDateTime.run {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }

    }

    fun changeHourAndMinute(hourOfDay: Int, minute: Int) {
        vmTaskDueDateTime.run {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
    }

    fun changeEventType(selectIndex: Int) {
        vmTaskType = TaskType.values().first { it.ordinal == selectIndex }
    }

    fun initViewModelByTask(task: Task) {
        updateTask = task
        vmTaskContent = task.content
        vmTaskDueDateTime = task.dueDateTime
        vmTaskType = task.taskType
        isAddTask = false
    }
}