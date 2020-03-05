package com.knight.oneday.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType
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

    /**
     * 事件类型和数据库Int
     */
    @TypeConverter
    fun eventTypeToInt(eventType: EventType): Int = eventType.ordinal

    @TypeConverter
    fun intToEventType(int: Int): EventType = EventType.values().first { it.ordinal == int }

    /**
     * 事件状态和数据库Int
     */
    @TypeConverter
    fun eventStateToInt(eventState: EventState): Int = eventState.ordinal

    @TypeConverter
    fun intToEventState(int: Int): EventState = EventState.values().first { it.ordinal == int }

}