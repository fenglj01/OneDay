package com.knight.oneday.mini

import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.viewmodels.BaseViewModel

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(private val repository: EventRepository) : BaseViewModel() {

    var eventList = repository.searchEventsWithSteps()

    fun updateEventDoneStatus(event: Event, isDone: Boolean) {
        launchOnIO(
            tryBlock = {
                repository.updateEventDoneStatus(
                    eventId = event.eventId,
                    isDone = isDone
                )
            }
        )
    }

    fun removeEvent(event: Event) {
        launchOnIO(
            tryBlock = {
                repository.removeEventById(event.eventId)
            }
        )
    }

    fun updateStepState(isDone: Boolean, stepId: Long) {
        launchOnIO(
            tryBlock = {
                repository.updateStepState(isDone, stepId).run {

                }
            }, catchBlock = {

            }
        )
    }

    fun refreshList() {
        eventList = repository.searchEventsWithSteps()
    }


}