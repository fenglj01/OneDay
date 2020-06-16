package com.knight.oneday.mini

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.TaskRepository

class MiniViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MiniViewModel(repository) as T
    }
}