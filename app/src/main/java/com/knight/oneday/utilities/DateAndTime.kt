package com.knight.oneday.utilities

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault

const val format24HStr = "yyyy-MM-dd HH:mm:ss"
const val format12HStr = "yyyy-MM-dd hh:mm:ss"
const val formatYearMonthDay = "yyyy-MM-dd"
const val formatYearMonth = "yyyy-MM"
const val formatHourMin = "HH:mm"

val AllFormat12H = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", getDefault())

fun currentTimeMills(): Long = System.currentTimeMillis()

fun currentCalendar(): Calendar =
    GregorianCalendar.getInstance().apply { timeInMillis = currentTimeMills() }

fun currentDayOfMonth(): Int = currentCalendar()[Calendar.DAY_OF_MONTH]

fun current24Hex(): String = currentTimeMills().format24Hex()

fun current12Hex(): String = currentTimeMills().format12Hex()

fun currentYearMonthDay(): String = currentTimeMills().formatYearMonthDay()

fun currentYearMonth(): String = currentTimeMills().formatYearMonth()

fun currentHourMin(): String = currentTimeMills().formHourMin()

fun Long.format24Hex(): String {
    return SimpleDateFormat(format24HStr, getDefault()).format(this)
}

fun Long.format12Hex(): String = SimpleDateFormat(format12HStr, getDefault()).format(this)

fun Long.formatYearMonthDay(): String =
    SimpleDateFormat(formatYearMonthDay, getDefault()).format(this)

fun Long.formatYearMonth(): String = SimpleDateFormat(formatYearMonth, getDefault()).format(this)

fun Long.formHourMin(): String = SimpleDateFormat(formatHourMin, getDefault()).format(this)