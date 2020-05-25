package com.knight.oneday.viewmodels

import com.knight.oneday.data.EventRepository

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(private val repository: EventRepository) : BaseViewModel() {

    val eventList = repository.getEventsWithSteps()

}