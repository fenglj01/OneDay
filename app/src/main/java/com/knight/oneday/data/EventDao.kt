package com.knight.oneday.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.TABLE_NAME_EVENT
import java.util.*

/**
 * @author knight
 * create at 20-2-29 下午3:23
 * 事件事务
 */
@Dao
interface EventDao {

    @Query("SELECT * FROM $TABLE_NAME_EVENT WHERE state = 0")
    fun getAllUnFinishedEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM $TABLE_NAME_EVENT GROUP BY state,create_time ORDER BY due_date_time desc")
    fun getAllEvent(): LiveData<List<Event>>

    @Query("UPDATE $TABLE_NAME_EVENT SET state =:eventState WHERE id =:id")
    suspend fun updateEventState(eventState: EventState, id: Long)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("SELECT * FROM $TABLE_NAME_EVENT ORDER BY due_date_time desc")
    fun getEventsWithSteps(): LiveData<List<EventAndEventSteps>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event): Long

    @Query("DELETE FROM $TABLE_NAME_EVENT")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_NAME_EVENT WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM (SELECT * FROM $TABLE_NAME_EVENT ORDER BY state asc) ORDER BY due_date_time desc")
    fun searchEventsByAll(): LiveData<List<EventAndEventSteps>>

    @Query("SELECT * FROM $TABLE_NAME_EVENT WHERE state =0 ORDER BY due_date_time desc")
    fun searchEventsByUnFinished(): LiveData<List<EventAndEventSteps>>

}