package com.knight.oneday.data

import androidx.room.TypeConverter
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.TaskType
import com.knight.oneday.utilities.format24HStr
import com.knight.oneday.utilities.format24Hex
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by FLJ in 2020/3/3 9:43
 * 类型转换，使得room基源数据和包装类型之间进行转换
 */
class Converters {

    /**
     * 时间戳和日期
     */
     @TypeConverter
     fun calendarToTimeStamp(calendar: Calendar): Long = calendar.timeInMillis

     @TypeConverter
     fun timeStampToCalendar(timeStamp: Long): Calendar =
         Calendar.getInstance().apply { timeInMillis = timeStamp }


   /* @TypeConverter
    fun calendarToTimeStamp(calendar: Calendar): String = calendar.timeInMillis.format24Hex()

    @TypeConverter
    fun timeStampToCalendar(formatDate: String): Calendar =
        Calendar.getInstance().apply {
            val date = SimpleDateFormat(format24HStr).parse(formatDate)
            time = date
        }*/

    /**
     * 事件状态和数据库Int
     */
    @TypeConverter
    fun eventStateToInt(eventState: EventState): Int = eventState.ordinal

    @TypeConverter
    fun intToEventState(int: Int): EventState = EventState.values().first { it.ordinal == int }

    @TypeConverter
    fun eventTypeToInt(taskType: TaskType): Int = taskType.ordinal

    @TypeConverter
    fun intToEventType(int: Int): TaskType = TaskType.values().first { it.ordinal == int }

}