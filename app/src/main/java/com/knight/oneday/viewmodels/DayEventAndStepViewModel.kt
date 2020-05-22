package com.knight.oneday.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.Step
import com.knight.oneday.data.StepRepository
import com.knight.oneday.utilities.EventType
import kotlinx.coroutines.launch

/**
 * Create by FLJ in 2020/3/3 13:41
 * 事件viewModel
 */
class DayEventAndStepViewModel(
    private val eventRepository: EventRepository,
    private val stepRepository: StepRepository
) : ViewModel() {

}