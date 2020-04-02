package com.knight.oneday.utilities

import android.view.View
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

const val SELECTION_MODEL_DATE = 1
const val SELECTION_MODEL_RANGE = 2

const val SELECTION_CHOICE_TODAY = 1
const val SELECTION_CHOICE_NEXT_MONTH = 2
/**
 * @param selectionMode 选择模式 (1 日期 2 范围)
 * @param selectionChoice 当前默认选择的时间 (1 今天 2 下个月)
 */
fun View.getMaterialDatePickerBuilder(
    selectionMode: Int = SELECTION_MODEL_DATE,
    selectionChoice: Int = SELECTION_CHOICE_TODAY,
    isInputMode: Boolean = false
): MaterialDatePicker.Builder<*> {
    // 当前时间
    val todayMills = MaterialDatePicker.todayInUtcMilliseconds()
    return if (selectionMode == SELECTION_MODEL_DATE) {
        val builder = MaterialDatePicker.Builder.datePicker()
        val nextMonthMills = todayMills.nextMonthMills()
        val selectionMills =
            if (selectionChoice == SELECTION_CHOICE_TODAY) todayMills else nextMonthMills
        builder.setSelection(selectionMills)
        builder
    } else {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val nextMonthMills = todayMills.nextMonthMills()
        val selectionMills =
            if (selectionChoice == SELECTION_CHOICE_TODAY) Pair(todayMills, todayMills) else Pair(
                nextMonthMills,
                nextMonthMills
            )
        builder.setSelection(selectionMills)
        builder
    }
}

fun Long.nextMonthMills(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.roll(Calendar.MONTH, 1)
    return calendar.timeInMillis
}