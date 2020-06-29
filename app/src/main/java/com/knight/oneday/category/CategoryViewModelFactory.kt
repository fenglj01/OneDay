package com.knight.oneday.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.add.AddTaskViewModel
import com.knight.oneday.data.TaskRepository

class CategoryViewModelFactory(val rep: TaskRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(
            rep
        ) as T
    }
}