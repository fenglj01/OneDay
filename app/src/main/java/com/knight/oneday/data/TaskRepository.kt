package com.knight.oneday.data

import android.util.Log
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.utilities.format24Hex
import java.util.*

class TaskRepository private constructor(
    private val taskDao: TaskDao
) {

    suspend fun searchTaskByDay(startTime: Calendar, endTime: Calendar): List<Task> {
        return taskDao.getTaskByRange(
            startTime,
            endTime
        )
    }

    suspend fun searchTaskByType(taskType: TaskType) = taskDao.getTaskByType(taskType)

    suspend fun updateEventDoneStatus(eventId: Long, isDone: Boolean) =
        taskDao.updateEventDoneStatus(if (isDone) 1 else 0, eventId)

    suspend fun deleteTask(taskId: Long) {
        taskDao.deleteById(taskId)
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