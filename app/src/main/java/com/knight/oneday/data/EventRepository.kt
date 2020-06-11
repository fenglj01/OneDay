package com.knight.oneday.data

import com.knight.oneday.utilities.EventState
import com.knight.oneday.setting.SettingPreferences

/**
 * Create by FLJ in 2020/3/2 14:52
 * 事件仓库，仓库也严格的使用单例来保证严谨
 */
class EventRepository private constructor(
    private val eventDao: EventDao,
    private val stepDao: StepDao
) {

    suspend fun updateEventDoneStatus(eventId: Long, isDone: Boolean) =
        eventDao.updateEventDoneStatus(if (isDone) 1 else 0, eventId)

    fun searchEventsWithStepsByAll() = eventDao.searchEventsByAll()

    fun searchEventsWithStepsByUnFinished() = eventDao.searchEventsByUnFinished()

    fun searchEventsWithSteps() =
        if (SettingPreferences.showAllowFinished) eventDao.searchEventsByAll() else eventDao.searchEventsByUnFinished()

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun createEvent(event: Event): Long = eventDao.insert(event)


    suspend fun removeAllEvent() {
        eventDao.deleteAll()
    }

    suspend fun removeEventById(id: Long) {
        eventDao.deleteById(id)
    }

    suspend fun updateStepState(isDone: Boolean, stepId: Long) {
        stepDao.updateStepState(if (isDone) EventState.FINISHED else EventState.UNFINISHED, stepId)
    }


    companion object {
        private var INSTANCE: EventRepository? = null

        fun getInstance(eventDao: EventDao, stepDao: StepDao): EventRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: EventRepository(eventDao, stepDao).also { INSTANCE = it }
            }
        }
    }
}