package com.knight.oneday.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.Step
import com.knight.oneday.data.StepRepository
import com.knight.oneday.utilities.EventType
import com.knight.oneday.utilities.TimeInterval
import com.knight.oneday.utilities.TimeUtils
import kotlinx.coroutines.launch

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
        eventRepository.getAllEvent()
    val steps =
        stepRepository.getStepsByEventId(1)
    
    val eventsWithSteps =
        eventRepository.getEventsWithSteps()

    fun addEvent() {
        viewModelScope.launch {
        }
    }

}