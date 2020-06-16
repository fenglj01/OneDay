package com.knight.oneday.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.knight.oneday.utilities.TABLE_NAME_EVENT

/**
 * @author knight
 * create at 20-2-29 下午3:23
 * 事件事务
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM $TABLE_NAME_EVENT WHERE is_done = 1")
    fun getAllUnFinishedEvents(): LiveData<List<Task>>

    @Query("SELECT * FROM $TABLE_NAME_EVENT GROUP BY is_done,create_time ORDER BY due_date_time desc")
    fun getAllEvent(): LiveData<List<Task>>

    /* @Query("UPDATE $TABLE_NAME_EVENT SET state =:eventState WHERE id =:id")
     suspend fun updateEventState(eventState: EventState, id: Long)*/
    @Query("UPDATE $TABLE_NAME_EVENT SET is_done =:isDone WHERE id =:id")
    suspend fun updateEventDoneStatus(isDone: Int, id: Long)

    @Update
    suspend fun updateEvent(task: Task)

    @Query("SELECT * FROM $TABLE_NAME_EVENT ORDER BY due_date_time desc")
    fun getEventsWithSteps(): LiveData<List<TaskAndEventSteps>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task): Long

    @Query("DELETE FROM $TABLE_NAME_EVENT")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_NAME_EVENT WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM (SELECT * FROM $TABLE_NAME_EVENT ORDER BY due_date_time desc) ORDER BY is_done asc")
    fun searchEventsByAll(): LiveData<List<TaskAndEventSteps>>

    @Query("SELECT * FROM $TABLE_NAME_EVENT WHERE is_done =0 ORDER BY due_date_time desc")
    fun searchEventsByUnFinished(): LiveData<List<TaskAndEventSteps>>

}