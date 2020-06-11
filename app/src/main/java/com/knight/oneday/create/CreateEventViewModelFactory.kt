package com.knight.oneday.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.StepRepository

class CreateEventViewModelFactory(
    private val eventRes: EventRepository,
    private val stepRes: StepRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateEventViewModel(
            eventRes,
            stepRes
        ) as T
    }

}