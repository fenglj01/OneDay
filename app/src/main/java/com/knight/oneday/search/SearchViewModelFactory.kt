package com.knight.oneday.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.TaskRepository

class SearchViewModelFactory(val rep: TaskRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(
            rep
        ) as T
    }
}