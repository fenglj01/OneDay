package com.knight.oneday.data

import android.util.Log
import com.knight.oneday.utilities.format24Hex
import java.util.*

class TaskRepository private constructor(
    private val taskDao: TaskDao
) {

    suspend fun searchTaskByDay(startTime: Calendar, endTime: Calendar): List<Task> {
        Log.d("TaskViewModel", "${startTime.timeInMillis} ${endTime.timeInMillis}")
        return taskDao.getTaskByRange(
            startTime,
            endTime
        )
    }


    companion object {
        private var INSTANCE: TaskRepository? = null

        fun getInstance(taskDao: TaskDao): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepository(taskDao).also { INSTANCE = it }
            }
        }
    }

}