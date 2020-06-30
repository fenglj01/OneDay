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

    fun searchByType(taskType: TaskType) {
        launchOnIO(
            tryBlock = {
                rep.searchTaskByType(taskType).let {
                    _taskList.postValue(it)
                }
            }
        )
    }
}