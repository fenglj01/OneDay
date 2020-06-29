package com.knight.oneday.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskRepository
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.viewmodels.BaseViewModel

class CategoryViewModel(private val rep: TaskRepository) : BaseViewModel() {

    var taskType = TaskType.NO_CATEGORY
        set(value) {
            if (value == field) return
            field = value
            _taskList.postValue(rep.searchTaskByType(field).value)
        }

    private var _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList
}