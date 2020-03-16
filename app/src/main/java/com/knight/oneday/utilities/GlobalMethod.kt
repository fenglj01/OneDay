package com.knight.oneday.utilities

import com.knight.oneday.OneDayApp
import com.knight.oneday.R
import java.util.*

/**
 * 根据时间获得分段信息
 * 提醒时间和创建时间一直则为无日期(ps：目前有bug 比如翻年了)
 */
fun reminderSection(createTime: Calendar, reminderTime: Calendar): String {
    return if (createTime.timeInMillis == reminderTime.timeInMillis) getString(R.string.section_no_date) else {
        val now = Calendar.getInstance()[Calendar.DAY_OF_YEAR]
        val remind = reminderTime[Calendar.DAY_OF_YEAR]
        when (remind - now) {
            0 -> getString(R.string.section_today)
            1 -> getString(R.string.section_tomorrow)
            2 -> getString(R.string.section_after_tomorrow)
            else -> getString(R.string.section_by_date).format(
                reminderTime[Calendar.MONTH] + 1,
                reminderTime[Calendar.DAY_OF_MONTH]
            )
        }
    }
}

fun getString(id: Int): String = OneDayApp.instance().getString(id)
