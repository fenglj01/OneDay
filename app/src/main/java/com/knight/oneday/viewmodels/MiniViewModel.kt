package com.knight.oneday.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.utilities.EventType
import com.knight.oneday.utilities.TimeUtils
import kotlinx.coroutines.launch

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(val repository: EventRepository) : ViewModel() {

    var todayStr = TimeUtils.getTodayMonthAndDayStr()

    val eventList = repository.getAllEvent()

    fun addEvent(content: String) {
        viewModelScope.launch {
            repository.createEvent(
                Event(
                    content = content,
                    type = EventType.MINI_NORMAL
                )
            )
            todayStr = TimeUtils.getTodayMonthAndDayStr()
        }
    }


}