package com.knight.oneday.data

import androidx.room.*
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType
import com.knight.oneday.utilities.TABLE_NAME_EVENT
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
    @ColumnInfo(name = "completion_time") var completionTime: Calendar = createTime,
    @ColumnInfo(name = "due_date_time") var dueDateTime: Calendar = createTime,
    @ColumnInfo(name = "is_done") var isDone: Boolean = false
) {
    /**
     * 主键的优化写法
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var eventId: Long = 0

    override fun toString(): String {
        return "$eventId - $content -$isDone"
    }
}
