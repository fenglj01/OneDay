package com.knight.oneday.utilities

import android.os.Parcel
import android.view.View
import androidx.annotation.StringRes
import androidx.core.util.Pair
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.knight.oneday.R
import java.util.*

const val SELECTION_MODEL_DATE = 1
const val SELECTION_MODEL_RANGE = 2

const val SELECTION_CHOICE_TODAY = 1
const val SELECTION_CHOICE_NEXT_MONTH = 2
const val SELECTION_CHOICE_DEFAULT = 3
/**
 * @param selectionMode 选择模式 (1 日期 2 范围)
 * @param selectionChoice 当前默认选择的时间 (1 今天 2 下个月)
 */
fun View.getMaterialDatePickerBuilder(
    selectionMode: Int = SELECTION_MODEL_DATE,
    selectionChoice: Int = SELECTION_CHOICE_DEFAULT,
    isInputMode: Boolean = false,
    @StringRes selectionTitle: Int = R.string.selection_date,
    boundsChoice: Int = CALENDAR_BOUNDS_ONE_YEAR_FORWARD,
    openChoice: Int = CALENDAR_OPEN_TODAY,
    validationChoice: Int = CALENDAR_VALIDATION_TODAY_FORWARD
): MaterialDatePicker.Builder<*> {
    // 当前时间
    val todayMills = MaterialDatePicker.todayInUtcMilliseconds()
    return if (selectionMode == SELECTION_MODEL_DATE) {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText(selectionTitle)
        if (selectionChoice != SELECTION_CHOICE_DEFAULT) {
            val nextMonthMills = todayMills.nextMonthMills()
            val selectionMills =
                if (selectionChoice == SELECTION_CHOICE_TODAY) todayMills else nextMonthMills
            builder.setSelection(selectionMills)
        }
        builder.setCalendarConstraints(
            todayMills.getCalendarConstraintsBuilder(
                boundsChoice,
                openChoice,
                validationChoice
            ).build()
        )
        builder
    } else {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val nextMonthMills = todayMills.nextMonthMills()
        if (selectionChoice != SELECTION_CHOICE_DEFAULT) {
            val selectionMills =
                if (selectionChoice == SELECTION_CHOICE_TODAY) Pair(
                    todayMills,
                    todayMills
                ) else Pair(
                    nextMonthMills,
                    nextMonthMills
                )
            builder.setCalendarConstraints(
                todayMills.getCalendarConstraintsBuilder(
                    boundsChoice,
                    openChoice,
                    validationChoice
                ).build()
            )
            builder.setSelection(selectionMills)
        }
        builder.setTitleText(selectionTitle)
        builder
    }
}

// 选择范围 默认(全部) 当年 一年后(具体其实可以配置)
const val CALENDAR_BOUNDS_DEFAULT = 0
const val CALENDAR_BOUNDS_THIS_YEAR = 1
const val CALENDAR_BOUNDS_ONE_YEAR_FORWARD = 2
// 当前选择打开的 默认 今天 下一个月
const val CALENDAR_OPEN_TODAY = 1
const val CALENDAR_OPEN_DEFAULT = 0
const val CALENDAR_OPEN_NEXT_MONTH = 2
// 可选条件 默认(范围内都可选) 今天以后可选
const val CALENDAR_VALIDATION_DEFAULT = 0
const val CALENDAR_VALIDATION_TODAY_FORWARD = 1

fun Long.getCalendarConstraintsBuilder(
    boundsChoice: Int = CALENDAR_BOUNDS_DEFAULT,
    openChoice: Int = CALENDAR_OPEN_DEFAULT,
    validationChoice: Int = CALENDAR_VALIDATION_DEFAULT
): CalendarConstraints.Builder {
    val builder = CalendarConstraints.Builder()
    val calendar =
        Calendar.getInstance().apply { timeInMillis = this@getCalendarConstraintsBuilder }
    when (boundsChoice) {
        CALENDAR_BOUNDS_DEFAULT -> {
        }
        CALENDAR_BOUNDS_THIS_YEAR -> {
            calendar[Calendar.MONTH] = Calendar.JANUARY
            val janThisYear = calendar.timeInMillis
            builder.setStart(janThisYear)
            calendar.timeInMillis = this
            calendar[Calendar.MONTH] = Calendar.DECEMBER
            val decThisYear = calendar.timeInMillis
            builder.setEnd(decThisYear)
        }
        CALENDAR_BOUNDS_ONE_YEAR_FORWARD -> {
            calendar.roll(Calendar.YEAR, 1)
            val oneYearForward = calendar.timeInMillis
            builder.setEnd(oneYearForward)
        }
    }
    when (openChoice) {
        CALENDAR_OPEN_DEFAULT -> {
        }
        CALENDAR_OPEN_TODAY -> {
            builder.setOpenAt(this)
        }
        CALENDAR_OPEN_NEXT_MONTH -> {
            builder.setOpenAt(this.nextMonthMills())
        }
    }
    when (validationChoice) {
        CALENDAR_VALIDATION_DEFAULT -> {
        }
        CALENDAR_VALIDATION_TODAY_FORWARD -> {
            builder.setValidator(DateValidatorPointForward.now())
        }
    }
    return builder
}

fun Long.nextMonthMills(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.roll(Calendar.MONTH, 1)
    return calendar.timeInMillis
}