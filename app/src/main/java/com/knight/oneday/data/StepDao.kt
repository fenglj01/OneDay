package com.knight.oneday.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.knight.oneday.utilities.TABLE_NAME_STEP

/**
 * @author knight
 * create at 20-2-29 下午3:35
 * 步骤事务
 **/
@Dao
interface StepDao {

    @Query("SELECT * FROM $TABLE_NAME_STEP WHERE event_id = :eventId ORDER BY create_time ASC")
    fun getStepByEventId(eventId: Long): LiveData<List<StepDao>>

    @Query("DELETE FROM $TABLE_NAME_STEP WHERE id = :stepId")
    suspend fun deleteStepById(stepId: Long)

    @Query("DELETE FROM $TABLE_NAME_STEP WHERE event_id = :eventId")
    suspend fun deleteStepByEventId(eventId: Long)

    @Query("DELETE FROM $TABLE_NAME_STEP")
    suspend fun deleteAll()

}