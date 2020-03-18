package com.knight.oneday.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.isFinished
import com.knight.oneday.utilities.EventState
import com.knight.oneday.utilities.EventType
import com.knight.oneday.utilities.TimeUtils
import com.knight.oneday.utilities.miniIsImportant
import kotlinx.coroutines.launch

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(private val repository: EventRepository) : BaseViewModel() {

    var todayStr = TimeUtils.getTodayMonthAndDayStr()

    val eventList = repository.getAllEvent()
    // 可变
    private val _addEventContent = MutableLiveData("")
    private val _addEventType = MutableLiveData(EventType.MINI_NORMAL)
    // 只读
    var addContent = _addEventContent
    var addEventType = _addEventType


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

    fun changeEventState(eventId: Long) {
        val event = eventList.value?.first { it.eventId == eventId }
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

    fun changeEventType() {
        _addEventType.value =
            if (_addEventType.value?.miniIsImportant() != false) EventType.MINI_NORMAL else EventType.MINI_IMPORTANT
    }

}