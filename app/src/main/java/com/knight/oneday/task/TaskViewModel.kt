package com.knight.oneday.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskAndStepRepository
import com.knight.oneday.data.TaskRepository
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.BaseViewModel
import java.util.*

/**
 * Create by FLJ in 2020/6/16 9:37
 * 新版主页ViewModel
 */
class TaskViewModel(private val taskRep: TaskRepository) : BaseViewModel() {

    var dayCurrent = currentCalendar()

    private val _taskList: MutableLiveData<List<Task>> = MutableLiveData()
    var taskList: LiveData<List<Task>> = _taskList

    val previewDateContent: String = currentWeekDayMonth()

    fun refreshTaskList() {
        launchOnIO(
            tryBlock = {
                taskRep.searchTaskByDay(dayCurrent.dayStartTime(), dayCurrent.dayEndTime()).run {
                    _taskList.postValue(this)
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