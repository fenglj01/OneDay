package com.knight.oneday.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.isFinished
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType
import com.knight.oneday.utilities.TimeUtils
import kotlinx.coroutines.launch

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(private val repository: EventRepository) : BaseViewModel() {

    var todayStr = TimeUtils.getTodayMonthAndDayStr()

    val eventList = repository.getAllEvent()

    fun addEvent(content: String) {
        launchOnIO(tryBlock = {
            repository.createEvent(
                Event(
                    content = content,
                    type = EventType.MINI_NORMAL
                )
            )
        })
    }

    fun changeEventState(position: Int) {
        val event = eventList.value?.get(position)
        event?.run {
            launchOnUI(
                tryBlock = {
                    val eventState =
                        if (isFinished()) EventState.UNFINISHED else EventState.FINISHED
                    repository.updateEventState(
                        eventState,
                        eventId
                    )
                    state = eventState
                }
            )
        }
    }

    fun finishEvent(eventId: Long, position: Int) {
        launchOnIO(tryBlock = {
            repository.updateEventState(
                eventState = EventState.FINISHED,
                id = eventId
            )
        })
    }

    fun cancelFinishedEvent(eventId: Long, position: Int) {
        launchOnIO(tryBlock = {
            repository.updateEventState(
                eventState = EventState.UNFINISHED,
                id = eventId
            )
        })
    }


}