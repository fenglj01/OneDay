package com.knight.oneday.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.EventRepository

class CreateEventViewModelFactory(private val repository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateEventViewModel(repository) as T
    }

}