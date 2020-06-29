package com.knight.oneday.utilities

import com.knight.oneday.OneDayApp
import com.knight.oneday.R
import com.knight.oneday.views.themeColor
import java.util.*


fun getString(id: Int): String = OneDayApp.instance().getString(id)

fun themeColor(attr: Int): Int = OneDayApp.instance().themeColor(attr)

fun getAppBarTitleByTaskType(taskType: TaskType): Int =
    when (taskType) {
        TaskType.NO_CATEGORY -> R.string.tag_no_tag
        TaskType.WORK -> R.string.tag_work
        TaskType.LIFE -> R.string.tag_life
        TaskType.ENTERTAINMENT -> R.string.tag_entertainment
        else -> R.string.tag_health
    }

fun getAppBarIconByTaskType(taskType: TaskType): Int =
    when (taskType) {
        TaskType.NO_CATEGORY -> R.mipmap.ic_tag_no_type
        TaskType.WORK -> R.mipmap.ic_tag_work
        TaskType.LIFE -> R.mipmap.ic_tag_life
        TaskType.ENTERTAINMENT -> R.mipmap.ic_tag_game
        else -> R.mipmap.ic_tag_health
    }