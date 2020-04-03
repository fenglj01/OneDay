package com.knight.oneday.utilities

import android.content.Context
import com.knight.oneday.data.AppDatabase
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.StepRepository
import com.knight.oneday.viewmodels.*

/**
 * Create by FLJ in 2020/3/3 13:43
 * viewModel注入
 */
object InjectorUtils {

    fun dayEventAndStepViewModelFactory(context: Context): DayEventAndStepViewModel {
        return DayEventAndStepViewModel(
            getEventRepository(context),
            getStepRepository(context)
        )
    }

    fun miniEventViewModelFactory(context: Context): MiniViewModelFactory {
        return MiniViewModelFactory(getEventRepository(context))
    }

    fun createEventViewModelFactory(context: Context): CreateEventViewModelFactory {
        return CreateEventViewModelFactory(getEventRepository(context))
    }

    private fun getEventRepository(context: Context): EventRepository =
        EventRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).eventDao())

    private fun getStepRepository(context: Context): StepRepository =
        StepRepository.getInstance((AppDatabase.getDatabase(context.applicationContext).stepDao()))
}