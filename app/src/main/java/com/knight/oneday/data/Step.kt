package com.knight.oneday.data

import androidx.room.*
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.TABLE_NAME_STEP
import java.util.*

/**
 * @author knight
 * create at 20-2-29 下午2:46
 * 步骤表(只有事件为重要事件的时候才会有步骤数据)
 */
@Entity(
    tableName = TABLE_NAME_STEP,
    /**
     * 外键链接
     * onDelete = ForeignKey.CASCADE 次级连删 当event删除时 相关的step也会被删除
     */
    foreignKeys = [ForeignKey(
        entity = Event::class,
        parentColumns = ["id"],
        childColumns = ["event_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("event_id")]
)
data class Step(
    val content: String,
    @ColumnInfo(name = "create_time") val createTime: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "completion_time") var completionTime: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "serial_number") var serialNumber: Int = 0,
    @ColumnInfo(name = "event_id") val eventId: Long,
    var state: EventState = EventState.UNFINISHED
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var stepId: Long = 0

    override fun toString(): String {
        return "id $stepId - eventId $eventId content $content"
    }
}