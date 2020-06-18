package com.knight.oneday.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.knight.oneday.data.Task
import com.knight.oneday.data.TaskAndStepRepository
import com.knight.oneday.data.Step
import com.knight.oneday.data.StepRepository
import com.knight.oneday.utilities.*
import com.knight.oneday.viewmodels.BaseViewModel
import com.knight.oneday.views.step.CreateStepContent
import com.knight.oneday.views.step.STEP_STATE_UNFINISHED
import java.util.*

/**
 * Create by FLJ in 2020/4/3 13:31
 * 创建事件ViewModel
 */
class CreateEventViewModel(
    private val taskAndStepRes: TaskAndStepRepository,
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
            taskAndStepRes.createEvent(createEventBean()).let { eventId ->
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

    private fun createEventBean(): Task {
        val createTime = Calendar.getInstance()
        val remindTime = if (eventRemindDate != null) Calendar.getInstance().apply {
            timeInMillis = eventRemindDate!!
        } else createTime
        return Task(
            content = eventContent,
            dueDateTime = remindTime
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