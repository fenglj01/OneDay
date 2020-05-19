package com.knight.oneday.views

import android.util.Log
import android.view.View
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
import com.knight.oneday.utilities.*
import com.knight.oneday.views.choice.WheelStringAdapter
import com.knight.oneday.views.choice.WheelStringViewHolder
import kotlinx.android.synthetic.main.dialog_date_time_chioce.*

/**
 * Create by FLJ in 2020/5/11 14:20
 * 选择日期时间底部Dialog
 */
class DateTimeChoiceDialogFragment : BaseBottomDialogFragment() {

    var onDateTimeChoiceFinish: OnDateTimeChoiceFinish? = null
    private var choiceCalendar: Calendar? = null
    private val hourList: List<String> by lazy { prepareHourListBySetting() }
    private lateinit var timeNumber: IntArray

    override

    fun dialogId(): Int = R.layout.dialog_date_time_chioce

    override fun initView() {
        prepareBySetting()
        choice_hint_tv.text = System.currentTimeMillis().yearAndMonthFormat()
        tv_current_day.text = nowDayOfMonth()
        fl_current.singleClick {
            calendar_view.scrollToCurrent()
        }
        hour_wheel_view.setOnItemSelectedListener { hourIndex ->
            timeNumber[hourIndex]
        }
    }

    private fun prepareBySetting() {
        // 设置日期根据设置的内容来
        hour_wheel_view.setAdapter(WheelStringAdapter(hourList))
        // 设置一个当前的默认时间
        hour_wheel_view.setCurrentItem(4)
        am_pm_view.visibility = if (SettingPreferences.is12HMode()) View.VISIBLE else View.GONE
    }

    override fun initEvent() {
        calendar_view.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                choice_hint_tv.text = calendar?.timeInMillis?.yearAndMonthFormat()
                choiceCalendar = calendar
            }

            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }
        })
        done_tv.singleClick {
            dialog?.dismiss()
            onDateTimeChoiceFinish?.onDateTimeChoiceFinish(choiceCalendar)
        }
    }

    override fun initData() {

    }

    private fun prepareHourListBySetting(): List<String> {
        timeNumber = resources.getIntArray(R.array.time_number)
        return if (SettingPreferences.is12HMode()) {
            timeNumber.asSequence().filter { it < 13 }.map { "$it:00" }.toList()
        } else {
            timeNumber.asSequence().map { "$it:00" }.toList()
        }

    }


    interface OnDateTimeChoiceFinish {
        fun onDateTimeChoiceFinish(calendar: Calendar?)
    }
}