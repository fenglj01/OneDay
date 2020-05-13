package com.knight.oneday.utilities

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by FLJ in 2020/3/3 13:53
 */
object TimeUtils {

    fun getTodayMonthAndDayStr(): String {
        val now = Calendar.getInstance()
        return "${now[Calendar.MONTH] + 1}-${now[Calendar.DAY_OF_MONTH]}"
    }

    fun getTodayTimeInterval(): TimeInterval {
        var now = Calendar.getInstance()
        return TimeInterval(getZeroTime(now), getZero23Time(now))
    }

    fun getZeroTime(calendar: Calendar = Calendar.getInstance()): Calendar {
        return calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
    }

    fun getZero23Time(calendar: Calendar = Calendar.getInstance()): Calendar {
        return calendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }
    }

    fun getBeforeTimeByDay(
        startTime: Calendar = Calendar.getInstance(),
        beforeDay: Int
    ): Calendar {
        return startTime.apply {
            set(
                Calendar.DATE,
                startTime.get(Calendar.DATE) - beforeDay
            )
        }
    }
}

data class TimeInterval(val startTime: Calendar, val endTime: Calendar)

@SuppressLint("SimpleDateFormat")
fun Long.timeMillsFormat(): String {
    val format = SimpleDateFormat("yyyy年M月d日")
    return format.format(this)
}
val ymFormat = SimpleDateFormat("M-yyyy")
fun Long.yearAndMonthFormat(): String {
    return ymFormat.format(this)
}

fun nowDayOfMonth():String{
    return "${Calendar.getInstance().get(Calendar.DAY_OF_MONTH)}"
}
