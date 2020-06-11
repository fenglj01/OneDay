package com.knight.oneday.add

import com.knight.oneday.data.EventRepository
import com.knight.oneday.utilities.currentHourMin
import com.knight.oneday.utilities.currentTimeMills
import com.knight.oneday.utilities.currentWeekDayMonth
import com.knight.oneday.viewmodels.BaseViewModel

class AddEventViewModel(val rep: EventRepository) : BaseViewModel() {

    val previewDateContent: String = currentWeekDayMonth()
    val previewTimeContent: String = currentHourMin()

}