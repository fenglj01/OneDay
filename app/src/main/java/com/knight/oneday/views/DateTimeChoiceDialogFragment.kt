package com.knight.oneday.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
import com.knight.oneday.data.RemindDate
import com.knight.oneday.utilities.*
import com.knight.oneday.views.choice.AmPmChoiceView
import com.knight.oneday.views.choice.WheelStringAdapter
import kotlinx.android.synthetic.main.dialog_date_time_chioce.*

/**
 * Create by FLJ in 2020/5/11 14:20
 * 选择日期时间底部Dialog
 */
class DateTimeChoiceDialogFragment : BaseBottomDialogFragment() {

    private val hourList: List<String> by lazy { prepareHourList() }
    private lateinit var timeNumber: IntArray
    private var timeHourIndex: Int = 4
    private var isChoiceAm: Boolean = true
    private var selectedCalendar: Calendar? = null
    /*选择时间的LiveData*/
    private val _choiceCalendar: MutableLiveData<RemindDate> = MutableLiveData()
    val choiceCalendar: LiveData<RemindDate>
        get() = _choiceCalendar


    override fun dialogId(): Int = R.layout.dialog_date_time_chioce

    override fun initView() {

        hour_wheel_view.setAdapter(WheelStringAdapter(hourList))
        hour_wheel_view.setOnItemSelectedListener { hourIndex ->
            timeHourIndex = hourIndex
        }

        choice_hint_tv.text = System.currentTimeMillis().yearAndMonthFormat()
        tv_current_day.text = nowDayOfMonth()

        fl_current.singleClick {
            calendar_view.scrollToCurrent()
        }

        am_pm_view.onChoiceFinish = object : AmPmChoiceView.OnChoiceFinish {
            override fun onChoiceFinish(isAm: Boolean) {
                isChoiceAm = isAm
            }
        }

        initDefaultChoice()

    }

    private fun initDefaultChoice() {
        val defaultDate = RemindDate()
        defaultDate.apply {
            val hour = defaultDate[RemindDate.HOUR_OF_DAY]
            val isAM = hour <= 12
            timeHourIndex = timeNumber.indexOfFirst { it == if (!isAM) hour - 12 else hour }
            hour_wheel_view.setCurrentItem(timeHourIndex)
            am_pm_view.toggle(isAM)
        }
        selectedCalendar = calendar_view.selectedCalendar
    }

    override fun initEvent() {

        calendar_view.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                choice_hint_tv.text = calendar?.timeInMillis?.yearAndMonthFormat()
                selectedCalendar = calendar
            }

            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }
        })

        done_tv.singleClick {
            dialog?.dismiss()
            prepareCreateDate()
        }

    }

    private fun prepareCreateDate() {
        selectedCalendar?.let { date ->
            val remindDate = RemindDate().apply {
                set(
                    date.year,
                    date.month - 1,
                    date.day
                )
                set(
                    RemindDate.HOUR_OF_DAY,
                    if (isChoiceAm) timeNumber[timeHourIndex] else timeNumber[timeHourIndex] + 12
                )
            }
            _choiceCalendar.postValue(remindDate)
        }
    }

    override fun initData() {

    }

    private fun prepareHourList(): List<String> {
        timeNumber = resources.getIntArray(R.array.time_number)
        return timeNumber.asSequence().map { "$it:00" }.toList()
    }

}