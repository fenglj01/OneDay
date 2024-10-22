package com.knight.oneday.utilities

import com.knight.oneday.R
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*
import java.util.Locale.getDefault

const val format24HStr = "yyyy-MM-dd HH:mm:ss"
const val format12HStr = "yyyy-MM-dd hh:mm:ss"
const val formatYearMonthDay = "yyyy-MM-dd"
const val formatYearMonth = "yyyy-MM"
const val formatHourMin = "HH:mm"
const val formatMonthDay = "MM-dd"
const val formatWeekDayMonth = "E,dd-MM"

val todayStr = getString(R.string.today)
val tomorrowStr = getString(R.string.tomorrow)
val yesterday = getString(R.string.yesterday)

val AllFormat12H = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", getDefault())

fun currentTimeMills(): Long = System.currentTimeMillis()

fun currentCalendar(): Calendar =
    GregorianCalendar.getInstance().apply { timeInMillis = currentTimeMills() }

fun currentDayOfMonth(): Int = currentCalendar()[Calendar.DAY_OF_MONTH]

fun current24Hex(): String = currentTimeMills().format24Hex()

fun current12Hex(): String = currentTimeMills().format12Hex()

fun currentYearMonthDay(): String = currentTimeMills().formatYearMonthDay()

fun currentYearMonth(): String = currentTimeMills().formatYearMonth()

fun currentHourMin(): String = currentTimeMills().formatHourMin()

fun currentWeekDayMonth(): String = currentTimeMills().formatWeekMonthDay()

fun Long.format24Hex(): String {
    return SimpleDateFormat(format24HStr, getDefault()).format(this)
}

fun Long.format12Hex(): String = SimpleDateFormat(format12HStr, getDefault()).format(this)

fun Long.formatYearMonthDay(): String =
    SimpleDateFormat(formatYearMonthDay, getDefault()).format(this)

fun Long.formatYearMonth(): String = SimpleDateFormat(formatYearMonth, getDefault()).format(this)

fun Long.formatHourMin(): String = SimpleDateFormat(formatHourMin, getDefault()).format(this)

fun Long.formatMonthDay(): String = SimpleDateFormat(formatMonthDay, getDefault()).format(this)

fun Long.formatWeekMonthDay(): String =
    SimpleDateFormat(formatWeekDayMonth, getDefault()).format(this)

fun Calendar.formatUi(): String {
    val year = get(Calendar.YEAR)
    val day = get(Calendar.DAY_OF_YEAR)
    val nowCalendar = currentCalendar()
    val nowYear = nowCalendar.get(Calendar.YEAR)
    val nowDay = nowCalendar.get(Calendar.DAY_OF_YEAR)
    return if (year == nowYear) {
        when {
            nowDay == day -> "$todayStr ${timeInMillis.formatHourMin()}"
            nowDay - day == 1 -> "$yesterday ${timeInMillis.formatHourMin()}"
            day - nowDay == 1 -> "$tomorrowStr ${timeInMillis.formatHourMin()}"
            else -> timeInMillis.formatMonthDay()
        }
    } else {
        timeInMillis.formatYearMonth()
    }
}

fun Calendar.getHourAndMin(): String = timeInMillis.formatHourMin()

fun Calendar.monthFirstDay(): Calendar {
    val first = Calendar.getInstance(getDefault())
    first.set(Calendar.MONTH, get(Calendar.MONTH))
    first.set(Calendar.DAY_OF_MONTH, 1)
    first.set(Calendar.HOUR_OF_DAY, 0)
    first.set(Calendar.MINUTE, 0)
    first.set(Calendar.SECOND, 0)
    return first
}

fun Calendar.monthEndDay(): Calendar {
    val end = Calendar.getInstance(getDefault())
    end.set(Calendar.MONTH, get(Calendar.MONTH))
    end.set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
    end.set(Calendar.HOUR_OF_DAY, 23)
    end.set(Calendar.MINUTE, 59)
    end.set(Calendar.SECOND, 59)
    return end
}

fun Calendar.dayStartTime(): Calendar {
    val startCalendar = Calendar.getInstance(getDefault())
    startCalendar.timeInMillis = timeInMillis
    startCalendar.set(Calendar.HOUR_OF_DAY, 0)
    startCalendar.set(Calendar.MINUTE, 0)
    startCalendar.set(Calendar.SECOND, 0)
    return startCalendar
}

fun Calendar.dayEndTime(): Calendar {
    val endCalendar = Calendar.getInstance(getDefault())
    endCalendar.timeInMillis = timeInMillis
    endCalendar.set(Calendar.HOUR_OF_DAY, 23)
    endCalendar.set(Calendar.MINUTE, 59)
    endCalendar.set(Calendar.SECOND, 59)
    return endCalendar
}