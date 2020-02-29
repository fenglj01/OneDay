package com.knight.oneday.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.knight.oneday.utilities.TABLE_NAME_EVENT

/**
 * @author knight
 * create at 20-2-29 下午2:46
 * 事件表
 */
@Entity(tableName = TABLE_NAME_EVENT)
data class Event(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val eventId: Long? = null,
    val content: String,
    @ColumnInfo(name = "create_time") val createTime: Long,
    @ColumnInfo(name = "completion_time") val completionTime: Long,
    val type: Int,
    val state: Int,
    @ColumnInfo(name = "now_step_id") var nowStepId: Long? = null
)