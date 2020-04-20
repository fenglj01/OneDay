package com.knight.oneday.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
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

    val eventList = repository.getEventsWithSteps()

}