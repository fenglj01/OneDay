package com.knight.oneday.mini

import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskRepository
import com.knight.oneday.viewmodels.BaseViewModel

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(private val repository: TaskRepository) : BaseViewModel() {

    var eventList = repository.searchEventsWithSteps()

    fun updateEventDoneStatus(task: Task, isDone: Boolean) {
        launchOnIO(
            tryBlock = {
                repository.updateEventDoneStatus(
                    eventId = task.eventId,
                    isDone = isDone
                )
            }
        )
    }

    fun removeEvent(task: Task) {
        launchOnIO(
            tryBlock = {
                repository.removeEventById(task.eventId)
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