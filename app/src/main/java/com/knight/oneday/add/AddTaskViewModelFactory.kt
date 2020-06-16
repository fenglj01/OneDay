package com.knight.oneday.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.TaskRepository

class AddTaskViewModelFactory(val rep: TaskRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddTaskViewModel(
            rep
        ) as T
    }
}