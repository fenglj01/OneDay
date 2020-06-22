package com.knight.oneday.task

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.annotation.IntegerRes
import androidx.databinding.BindingAdapter
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
import com.knight.oneday.calendar.CalendarToolView
import com.knight.oneday.data.Task
import com.knight.oneday.utilities.TaskType

@BindingAdapter("bindSelectedDay")
fun CalendarToolView.bindSelectedDay(day: String) {
    changeSelectedDay(day)
}

@BindingAdapter("bindCalendarSelectedListener")
fun CalendarView.bindCalendarSelectedListener(listener: CalendarView.OnCalendarSelectListener) {
    setOnCalendarSelectListener(listener)
}


@BindingAdapter("bindTagImageSrc")
fun ImageView.bindTagImageSrc(type: TaskType) {
    val src = when (type) {
        TaskType.WORK -> R.mipmap.ic_tag_work
        TaskType.ENTERTAINMENT -> R.mipmap.ic_tag_game
        TaskType.LIFE -> R.mipmap.ic_tag_life
        TaskType.HEALTH -> R.mipmap.ic_tag_health
        else -> R.mipmap.ic_tag_no_type
    }
    setImageResource(src)
}
