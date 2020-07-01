package com.knight.oneday.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskRepository
import com.knight.oneday.viewmodels.BaseViewModel

class SearchViewModel(private val rep: TaskRepository) : BaseViewModel() {

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    private var currentSearchText: String = ""

    fun searchByText(text: String) {
        if (text.isNullOrEmpty()) return
        currentSearchText = text
        launchOnIO(
            tryBlock = {
                rep.searchTaskByText("%$text%").run {
                    Log.d("SearchText", "$text - ${this.size}")
                    _taskList.postValue(this)
                }
            }
        )
    }

    fun changeTaskStatus(task: Task, isDone: Boolean) {
        launchOnIO(
            tryBlock = {
                rep.updateEventDoneStatus(task.taskId, isDone).run {
                    searchByText(currentSearchText)
                }
            }
        )
    }

    fun deleteTask(task: Task) {
        launchOnIO(
            tryBlock = {
                rep.deleteTask(task.taskId).run {
                    searchByText(currentSearchText)
                }
            }
        )
    }

}