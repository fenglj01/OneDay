package com.knight.oneday.task

import com.knight.oneday.utilities.currentWeekDayMonth
import com.knight.oneday.viewmodels.BaseViewModel

/**
 * Create by FLJ in 2020/6/16 9:37
 * 新版主页ViewModel
 */
class TaskViewModel : BaseViewModel() {

    val previewDateContent: String = currentWeekDayMonth()

}