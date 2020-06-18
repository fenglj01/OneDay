package com.knight.oneday.mini

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.knight.oneday.data.TaskAndStepRepository

class MiniViewModelFactory(private val andStepRepository: TaskAndStepRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MiniViewModel(andStepRepository) as T
    }
}