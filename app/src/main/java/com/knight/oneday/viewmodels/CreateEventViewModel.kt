package com.knight.oneday.viewmodels

import com.google.android.material.datepicker.MaterialDatePicker
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.StepRepository
import com.knight.oneday.utilities.TimeUtils
import com.knight.oneday.utilities.timeMillsFormat

/**
 * Create by FLJ in 2020/4/3 13:31
 * 创建事件ViewModel
 */
class CreateEventViewModel(
    private val eventRes: EventRepository,
    private val stepRes: StepRepository
) : BaseViewModel() {
    var remindTimeMills = System.currentTimeMillis()
    var todayStr = remindTimeMills.timeMillsFormat()
}