package com.knight.oneday.viewmodels

import androidx.lifecycle.ViewModel
import com.knight.oneday.data.EventRepository
import com.knight.oneday.utilities.TimeUtils

/**
 * @author knight
 * create at 20-3-9 下午7:51
 * 极简风格的ViewModel
 */
class MiniViewModel(repository: EventRepository) : ViewModel() {

    val todayStr = TimeUtils.getTodayMonthAndDayStr()

}