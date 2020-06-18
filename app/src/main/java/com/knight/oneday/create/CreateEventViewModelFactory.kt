package com.knight.oneday.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.TaskAndStepRepository
import com.knight.oneday.data.StepRepository

class CreateEventViewModelFactory(
    private val taskAndStepRes: TaskAndStepRepository,
    private val stepRes: StepRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateEventViewModel(
            taskAndStepRes,
            stepRes
        ) as T
    }

}