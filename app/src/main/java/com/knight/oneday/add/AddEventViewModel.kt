package com.knight.oneday.add

import com.knight.oneday.data.EventRepository
import com.knight.oneday.utilities.currentHourMin
import com.knight.oneday.utilities.currentTimeMills
import com.knight.oneday.utilities.currentWeekDayMonth
import com.knight.oneday.viewmodels.BaseViewModel

class AddEventViewModel(val rep: EventRepository) : BaseViewModel() {

    val previewDateContent: String = currentWeekDayMonth()
    val previewTimeContent: String = currentHourMin()

    fun prepareHourMinStr(hourOfDay: Int, minute: Int) =
        "${if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"}:${if (minute < 10) "0$minute" else "$minute"}"

}