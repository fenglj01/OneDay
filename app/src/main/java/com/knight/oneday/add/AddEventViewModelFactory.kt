package com.knight.oneday.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.create.CreateEventViewModel
import com.knight.oneday.data.EventRepository

class AddEventViewModelFactory(val rep: EventRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddEventViewModel(
            rep
        ) as T
    }
}