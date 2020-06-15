package com.knight.oneday.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.nav.NavigationModelItem
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.BaseViewModel
import java.util.*

/**
 * Create by FLJ in 2020/6/15 15:09
 * 添加
 */
class AddEventViewModel(private val rep: EventRepository) : BaseViewModel() {

    companion object {
        const val ADD_STATUS_CONTENT_IS_EMPTY = 0
        const val ADD_STATUS_FAIL = 1
        const val ADD_STATUS_SUCCESS = 2
    }

    val previewDateContent: String = currentWeekDayMonth()
    val previewTimeContent: String = currentHourMin()

    var eventContent: String = ""
    var eventDueDateTime: Calendar = GregorianCalendar.getInstance()
    var eventType: EventType = EventType.NO_CATEGORY

    private val _addEventStatus: MutableLiveData<Int> = MutableLiveData()
    val addEventStatus: LiveData<Int> = _addEventStatus

    fun prepareHourMinStr(hourOfDay: Int, minute: Int) =
        "${if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"}:${if (minute < 10) "0$minute" else "$minute"}"


    fun addEvent() {
        if (!checkCanAdd()) {
            _addEventStatus.postValue(ADD_STATUS_CONTENT_IS_EMPTY)
            return
        }
        launchOnIO(
            tryBlock = {
                rep.createEvent(
                    event = Event(
                        content = eventContent,
                        dueDateTime = eventDueDateTime,
                        eventType = eventType
                    )
                ).run {
                    _addEventStatus.postValue(ADD_STATUS_SUCCESS)
                }
            },
            catchBlock = {
                _addEventStatus.postValue(ADD_STATUS_FAIL)
            }
        )
    }

    private fun checkCanAdd(): Boolean = eventContent.isNotEmpty()

    fun changeDate(year: Int, month: Int, day: Int) {
        eventDueDateTime.run {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }

    }

    fun changeHourAndMinute(hourOfDay: Int, minute: Int) {
        eventDueDateTime.run {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
    }

    fun changeEventType(selectIndex: Int) {
        eventType = EventType.values().first { it.ordinal == selectIndex }
    }
}