package com.knight.oneday.utilities

import android.content.Context
import com.knight.oneday.create.CreateEventViewModelFactory
import com.knight.oneday.data.AppDatabase
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.StepRepository
import com.knight.oneday.mini.MiniViewModelFactory
import com.knight.oneday.viewmodels.*

/**
 * Create by FLJ in 2020/3/3 13:43
 * viewModel注入
 */
object InjectorUtils {

    fun eventWithStepViewModelFactory(context: Context): MiniViewModelFactory {
        return MiniViewModelFactory(
            getEventWithStepRepository(context)
        )
    }

    fun createEventViewModelFactory(context: Context): CreateEventViewModelFactory {
        return CreateEventViewModelFactory(
            getEventWithStepRepository(context),
            getStepRepository(context)
        )
    }

    private fun getEventWithStepRepository(context: Context): EventRepository =
        EventRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).eventDao(),
            (AppDatabase.getDatabase(context.applicationContext).stepDao())
        )

    private fun getStepRepository(context: Context): StepRepository =
        StepRepository.getInstance((AppDatabase.getDatabase(context.applicationContext).stepDao()))
}