package com.knight.oneday.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.TaskAndStepRepository
import com.knight.oneday.data.TaskRepository

class TaskViewModelFactory(private val taskRep: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRep) as T
    }
}