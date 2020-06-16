package com.knight.oneday.data

import com.knight.oneday.utilities.EventState
import com.knight.oneday.setting.SettingPreferences

/**
 * Create by FLJ in 2020/3/2 14:52
 * 事件仓库，仓库也严格的使用单例来保证严谨
 */
class TaskRepository private constructor(
    private val taskDao: TaskDao,
    private val stepDao: StepDao
) {

    suspend fun updateEventDoneStatus(eventId: Long, isDone: Boolean) =
        taskDao.updateEventDoneStatus(if (isDone) 1 else 0, eventId)

    fun searchEventsWithStepsByAll() = taskDao.searchEventsByAll()

    fun searchEventsWithStepsByUnFinished() = taskDao.searchEventsByUnFinished()

    fun searchEventsWithSteps() =
        if (SettingPreferences.showAllowFinished) taskDao.searchEventsByAll() else taskDao.searchEventsByUnFinished()

    suspend fun updateEvent(task: Task) {
        taskDao.updateEvent(task)
    }

    suspend fun createEvent(task: Task): Long = taskDao.insert(task)


    suspend fun removeAllEvent() {
        taskDao.deleteAll()
    }

    suspend fun removeEventById(id: Long) {
        taskDao.deleteById(id)
    }

    suspend fun updateStepState(isDone: Boolean, stepId: Long) {
        stepDao.updateStepState(if (isDone) EventState.FINISHED else EventState.UNFINISHED, stepId)
    }


    companion object {
        private var INSTANCE: TaskRepository? = null

        fun getInstance(taskDao: TaskDao, stepDao: StepDao): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TaskRepository(taskDao, stepDao).also { INSTANCE = it }
            }
        }
    }
}