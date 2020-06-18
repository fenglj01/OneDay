package com.knight.oneday.mini

import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskAndStepRepository
import com.knight.oneday.viewmodels.BaseViewModel

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(private val andStepRepository: TaskAndStepRepository) : BaseViewModel() {

    var eventList = andStepRepository.searchEventsWithSteps()

    fun updateEventDoneStatus(task: Task, isDone: Boolean) {
        launchOnIO(
            tryBlock = {
                andStepRepository.updateEventDoneStatus(
                    eventId = task.eventId,
                    isDone = isDone
                )
            }
        )
    }

    fun removeEvent(task: Task) {
        launchOnIO(
            tryBlock = {
                andStepRepository.removeEventById(task.eventId)
            }
        )
    }

    fun updateStepState(isDone: Boolean, stepId: Long) {
        launchOnIO(
            tryBlock = {
                andStepRepository.updateStepState(isDone, stepId).run {

                }
            }, catchBlock = {

            }
        )
    }

    fun refreshList() {
        eventList = andStepRepository.searchEventsWithSteps()
    }


}