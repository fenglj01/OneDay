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
    private var _taskCount = MutableLiveData<CategoryCount>()
    val taskCount: LiveData<CategoryCount> = _taskCount

    private lateinit var currentTaskType: TaskType

    fun searchByType(taskType: TaskType) {
        currentTaskType = taskType
        launchOnIO(
            tryBlock = {
                rep.searchTaskByType(taskType).let {

                    _taskList.postValue(it)

                    prepareCategoryCount(it)
                }
            }
        )
    }

    private fun prepareCategoryCount(list: List<Task>) {
        if (list.isNotEmpty()) {
            val finishedCount =
                list.asSequence().filter { task -> task.isDone }.toList().size
            val expiredCount =
                list.asSequence().filter { task -> task.isExpired() }.toList().size
            _taskCount.postValue(
                CategoryCount(
                    finishedCount,
                    expiredCount,
                    list.size
                )
            )
        } else {
            _taskCount.postValue(
                CategoryCount(0, 0, 0)
            )
        }
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

    data class CategoryCount(val finished: Int, val expired: Int, val total: Int)
}