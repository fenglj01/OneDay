package com.knight.oneday.task

import android.view.View
import androidx.databinding.BindingAdapter
import com.haibin.calendarview.CalendarView
import com.knight.oneday.calendar.CalendarToolView
import com.knight.oneday.data.Task

@BindingAdapter("bindSelectedDay")
fun CalendarToolView.bindSelectedDay(day: String) {
    changeSelectedDay(day)
}

@BindingAdapter("bindCalendarSelectedListener")
fun CalendarView.bindCalendarSelectedListener(listener: CalendarView.OnCalendarSelectListener) {
    setOnCalendarSelectListener(listener)
}
