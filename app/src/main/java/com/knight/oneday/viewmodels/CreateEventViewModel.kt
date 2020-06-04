package com.knight.oneday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Event
import com.knight.oneday.data.EventRepository
import com.knight.oneday.data.Step
import com.knight.oneday.data.StepRepository
import com.knight.oneday.utilities.*
import com.knight.oneday.views.step.CreateStepContent
import com.knight.oneday.views.step.STEP_STATE_UNFINISHED
import java.util.*

/**
 * Create by FLJ in 2020/4/3 13:31
 * 创建事件ViewModel
 */
class CreateEventViewModel(
    private val eventRes: EventRepository,
    private val stepRes: StepRepository
) : BaseViewModel() {
    var remindYearMonthDay = currentYearMonthDay()

    var eventContent: String = ""
    var eventRemindDate: Long? = null

    var eventSteps: MutableList<CreateStepContent> = mutableListOf()

    private val _viewModelStatus: MutableLiveData<Int> = MutableLiveData()

    val viewModelStatus: LiveData<Int> = _viewModelStatus

    fun createEvent() {
        _viewModelStatus.postValue(VIEW_MODEL_STATUS_ON_IO)
        launchOnIO(tryBlock = {
            eventRes.createEvent(createEventBean()).let { eventId ->
                createStep(eventId)
            }
            _viewModelStatus.postValue(VIEW_MODEL_STATUS_ON_SUCCESS)
        }, catchBlock = {
            _viewModelStatus.postValue(VIEW_MODEL_STATUS_ON_FAIL)
        })
    }

    suspend fun createStep(eventId: Long) {
        if (eventSteps.isNotEmpty()) {
            eventSteps.forEach { createStepContent ->
                val step = createStepBean(createStepContent, eventId)
                stepRes.createStep(step)
            }
        }
    }

    private fun createEventBean(): Event {
        val createTime = Calendar.getInstance()
        val remindTime = if (eventRemindDate != null) Calendar.getInstance().apply {
            timeInMillis = eventRemindDate!!
        } else createTime
        return Event(
            content = eventContent,
            dueDateTime = remindTime,
            type = EventType.MINI_NORMAL
        )
    }

    private fun createStepBean(createStepContent: CreateStepContent, eventId: Long): Step {
        return Step(
            eventId = eventId,
            content = createStepContent.content,
            serialNumber = createStepContent.serialNumber,
            state = if (createStepContent.state == STEP_STATE_UNFINISHED) EventState.UNFINISHED else EventState.FINISHED
        )
    }

    fun submitSteps(list: List<CreateStepContent>) {
        eventSteps.clear()
        eventSteps.addAll(list)
    }
}