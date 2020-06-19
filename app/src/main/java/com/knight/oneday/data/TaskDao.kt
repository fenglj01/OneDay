package com.knight.oneday.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.knight.oneday.utilities.TABLE_NAME_TASK
import java.util.*

/**
 * @author knight
 * create at 20-2-29 下午3:23
 * 事件事务
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE due_date_time BETWEEN :startTime AND :endTime")
    suspend fun getTaskByRange(startTime: Calendar, endTime: Calendar): List<Task>

    @Query("SELECT * FROM $TABLE_NAME_TASK WHERE is_done = 1")
    fun getAllUnFinishedEvents(): LiveData<List<Task>>

    @Query("SELECT * FROM $TABLE_NAME_TASK ORDER BY due_date_time desc")
    fun getAllEvent(): LiveData<List<Task>>

    /* @Query("UPDATE $TABLE_NAME_EVENT SET state =:eventState WHERE id =:id")
     suspend fun updateEventState(eventState: EventState, id: Long)*/
    @Query("UPDATE $TABLE_NAME_TASK SET is_done =:isDone WHERE id =:id")
    suspend fun updateEventDoneStatus(isDone: Int, id: Long)

    @Update
    suspend fun updateEvent(task: Task)

    @Query("SELECT * FROM $TABLE_NAME_TASK ORDER BY due_date_time desc")
    fun getEventsWithSteps(): LiveData<List<TaskAndEventSteps>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task): Long

    @Query("DELETE FROM $TABLE_NAME_TASK")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_NAME_TASK WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM (SELECT * FROM $TABLE_NAME_TASK ORDER BY due_date_time desc) ORDER BY is_done asc")
    fun searchEventsByAll(): LiveData<List<TaskAndEventSteps>>

    @Query("SELECT * FROM $TABLE_NAME_TASK WHERE is_done =0 ORDER BY due_date_time desc")
    fun searchEventsByUnFinished(): LiveData<List<TaskAndEventSteps>>

}