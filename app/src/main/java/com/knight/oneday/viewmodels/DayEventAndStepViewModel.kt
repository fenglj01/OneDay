package com.knight.oneday.viewmodels

import androidx.lifecycle.ViewModel
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.StepRepository
import com.knight.oneday.utilities.TimeInterval
import com.knight.oneday.utilities.TimeUtils

/**
 * Create by FLJ in 2020/3/3 13:41
 * 事件viewModel
 */
class DayEventAndStepViewModel(
    private val eventRepository: EventRepository,
    private val stepRepository: StepRepository
) : ViewModel() {

    val timeInterval: TimeInterval = TimeUtils.getTodayTimeInterval()

    val events =
        eventRepository.getEventsByTimeInterval(timeInterval.startTime, timeInterval.endTime)

}