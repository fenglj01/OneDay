package com.knight.oneday.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskRepository
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.viewmodels.BaseViewModel

class CategoryViewModel(private val rep: TaskRepository) : BaseViewModel() {

    private var _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    private lateinit var currentTaskType: TaskType

    fun searchByType(taskType: TaskType) {
        currentTaskType = taskType
        launchOnIO(
            tryBlock = {
                rep.searchTaskByType(taskType).let {
                    _taskList.postValue(it)
                }
            }
        )
    }

    fun changeTaskStatus(task: Task, isDone: Boolean) {
        launchOnIO(
            tryBlock = {
                rep.updateEventDoneStatus(task.taskId, isDone).run {
                    searchByType(currentTaskType)
                }
            }
        )
    }

    fun deleteTask(task: Task) {
        launchOnIO(
            tryBlock = {
                rep.deleteTask(task.taskId).run {
                    searchByType(currentTaskType)
                }
            }
        )
    }
}