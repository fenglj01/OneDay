package com.knight.oneday.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.knight.oneday.utilities.TABLE_NAME_EVENT
import java.util.*

/**
 * @author knight
 * create at 20-2-29 下午3:23
 * 事件事务
 */
@Dao
interface EventDao {

    @Query("SELECT * FROM $TABLE_NAME_EVENT WHERE create_time BETWEEN :startTime AND :endTime")
    fun getEventByTimeInterval(startTime: Calendar, endTime: Calendar): LiveData<List<Event>>

    @Query("SELECT * FROM $TABLE_NAME_EVENT WHERE state = 0")
    fun getAllUnFinishedEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM $TABLE_NAME_EVENT")
    fun getAllEvent(): LiveData<List<Event>>

    @Query("SELECT * FROM $TABLE_NAME_EVENT")
    fun getEventsWithSteps(): LiveData<List<EventAndEventSteps>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event)

    @Query("DELETE FROM $TABLE_NAME_EVENT")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_NAME_EVENT WHERE id = :id")
    suspend fun deleteById(id: Long)
}