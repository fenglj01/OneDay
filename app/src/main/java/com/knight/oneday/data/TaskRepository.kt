package com.knight.oneday.data

import java.util.*

class TaskRepository private constructor(
    private val taskDao: TaskDao
) {

    suspend fun searchTaskByDay(startTime: Calendar, endTime: Calendar) =
        taskDao.getTaskByRange(startTime, endTime)


    companion object {
        private var INSTANCE: TaskRepository? = null

        fun getInstance(taskDao: TaskDao): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepository(taskDao).also { INSTANCE = it }
            }
        }
    }

}