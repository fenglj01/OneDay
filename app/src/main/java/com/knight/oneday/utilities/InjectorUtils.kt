package com.knight.oneday.utilities

import android.content.Context
import com.knight.oneday.data.AppDatabase
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.StepRepository
import com.knight.oneday.viewmodels.DayEventAndStepViewModel
import com.knight.oneday.viewmodels.MiniViewModel
import com.knight.oneday.viewmodels.MiniViewModelFactory

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

    private fun getEventRepository(context: Context): EventRepository =
        EventRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).eventDao())

    private fun getStepRepository(context: Context): StepRepository =
        StepRepository.getInstance((AppDatabase.getDatabase(context.applicationContext).stepDao()))
}