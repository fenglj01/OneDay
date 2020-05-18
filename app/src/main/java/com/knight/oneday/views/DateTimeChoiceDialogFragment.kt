package com.knight.oneday.views

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

    override fun dialogId(): Int = R.layout.dialog_date_time_chioce

    override fun initView() {
        choice_hint_tv.text = System.currentTimeMillis().yearAndMonthFormat()
        hour_wheel_view.setAdapter(WheelStringAdapter(resources.getStringArray(R.array.time_type_12).toList()))
        hour_wheel_view.setCurrentItem(4)
        tv_current_day.text = nowDayOfMonth()
        fl_current.singleClick {
            calendar_view.scrollToCurrent()
        }
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
        }
    }

    override fun initData() {
    }

    interface OnDateTimeChoiceFinish {
        fun onDateTimeChoiceFinish(calendar: Calendar)
    }
}