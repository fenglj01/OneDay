package com.knight.oneday.mini

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.EventRepository

class MiniViewModelFactory(private val repository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MiniViewModel(repository) as T
    }
}