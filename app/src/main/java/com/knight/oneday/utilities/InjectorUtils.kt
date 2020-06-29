package com.knight.oneday.utilities

import android.content.Context
import com.knight.oneday.add.AddTaskViewModelFactory
import com.knight.oneday.data.AppDatabase
import com.knight.oneday.data.TaskAndStepRepository
import com.knight.oneday.data.StepRepository
import com.knight.oneday.data.TaskRepository
import com.knight.oneday.task.TaskViewModelFactory

/**
 * Create by FLJ in 2020/3/3 13:43
 * viewModel注入
 */
object InjectorUtils {

    fun taskViewModelFactory(context: Context): TaskViewModelFactory {
        return TaskViewModelFactory(
            getTaskRepository(context)
        )
    }

    fun addEventViewModelFactory(context: Context): AddTaskViewModelFactory {
        return AddTaskViewModelFactory(
            getEventWithStepRepository(context)
        )
    }

    private fun getTaskRepository(context: Context): TaskRepository =
        TaskRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).eventDao())

    private fun getEventWithStepRepository(context: Context): TaskAndStepRepository =
        TaskAndStepRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).eventDao(),
            (AppDatabase.getDatabase(context.applicationContext).stepDao())
        )

    private fun getStepRepository(context: Context): StepRepository =
        StepRepository.getInstance((AppDatabase.getDatabase(context.applicationContext).stepDao()))


}