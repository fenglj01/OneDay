package com.knight.oneday.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType
import com.knight.oneday.utilities.TABLE_NAME_EVENT
import com.knight.oneday.utilities.UNFINISHED
import java.util.*

/**
 * @author knight
 * create at 20-2-29 下午2:46
 * 事件表
 */
@Entity(tableName = TABLE_NAME_EVENT)
data class Event(
    val content: String,
    @ColumnInfo(name = "create_time") val createTime: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "completion_time") var completionTime: Calendar = Calendar.getInstance(),
    var type: EventType,
    var state: EventState = EventState.UNFINISHED
    /*@ColumnInfo(name = "now_step_id") var nowStepId: Long? = null 当前步骤的方式 建议更换为查询的方式 具体怎么做后期再商量*/
) {
    /**
     * 主键的优化写法
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var eventId: Long = 0
}