package com.knight.oneday.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.knight.oneday.utilities.TABLE_NAME_STEP

/**
 * @author knight
 * create at 20-2-29 下午2:46
 * 步骤表(只有事件为重要事件的时候才会有步骤数据)
 */
@Entity(tableName = TABLE_NAME_STEP)
data class Step(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val stepId: Long? = null,
    val content: String,
    @ColumnInfo(name = "create_time") val createTime: Long,
    @ColumnInfo(name = "completion_time") val completionTime: Long,
    @ColumnInfo(name = "serial_number") val serialNumber: Int,
    @ColumnInfo(name = "event_id") val eventId: Long
)