package com.knight.oneday.data

import java.util.*

/**
 * Create by FLJ in 2020/3/2 14:52
 * 事件仓库，仓库也严格的使用单例来保证严谨
 */
class EventRepository private constructor(private val eventDao: EventDao) {

    fun getEventsByTimeInterval(startTime: Calendar, endTine: Calendar) =
        eventDao.getEventByTimeInterval(startTime, endTine)

    fun getAllEvent() = eventDao.getAllEvent()

    fun getEventsWithSteps() = eventDao.getEventsWithSteps()

    suspend fun createEvent(event: Event) {
        eventDao.insert(event)
    }

    suspend fun removeAllEvent() {
        eventDao.deleteAll()
    }

    suspend fun removeEventById(id: Long) {
        eventDao.deleteById(id)
    }

    companion object {
        private var INSTANCE: EventRepository? = null

        fun getInstance(eventDao: EventDao): EventRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: EventRepository(eventDao).also { INSTANCE = it }
            }
        }
    }
}