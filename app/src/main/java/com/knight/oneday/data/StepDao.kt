package com.knight.oneday.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.TABLE_NAME_STEP

/**
 * @author knight
 * create at 20-2-29 下午3:35
 * 步骤事务
 **/
@Dao
interface StepDao {

    @Query("SELECT * FROM $TABLE_NAME_STEP WHERE event_id = :eventId ORDER BY create_time ASC")
    fun getStepByEventId(eventId: Long): LiveData<List<Step>>

    @Query("DELETE FROM $TABLE_NAME_STEP WHERE id = :stepId")
    suspend fun deleteStepById(stepId: Long)

    @Query("DELETE FROM $TABLE_NAME_STEP WHERE event_id = :eventId")
    suspend fun deleteStepByEventId(eventId: Long)

    @Query("DELETE FROM $TABLE_NAME_STEP")
    suspend fun deleteAll()

    @Query("UPDATE $TABLE_NAME_STEP SET state =:state WHERE id =:id")
    suspend fun updateStepState(state: EventState, id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStep(step: Step)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<Step>)
}