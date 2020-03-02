package com.knight.oneday.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knight.oneday.utilities.TABLE_NAME_EVENT

/**
 * @author knight
 * create at 20-2-29 下午3:23
 * 事件事务
 */
@Dao
interface EventDao {

    @Query("SELECT * FROM $TABLE_NAME_EVENT WHERE create_time BETWEEN :startTime AND :endTime")
    fun getEventByTimeInterval(startTime: Long, endTime: Long): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: Event)

    @Query("DELETE FROM $TABLE_NAME_EVENT")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_NAME_EVENT WHERE id = :id")
    suspend fun deleteById(id: Long)
}