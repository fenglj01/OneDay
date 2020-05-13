package com.knight.oneday.views

import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
import com.knight.oneday.utilities.TimeUtils
import com.knight.oneday.utilities.timeMillsFormat
import com.knight.oneday.utilities.yearAndMonthFormat
import com.knight.oneday.views.choice.WheelStringAdapter
import com.knight.oneday.views.choice.WheelStringViewHolder
import kotlinx.android.synthetic.main.dialog_date_time_chioce.*

/**
 * Create by FLJ in 2020/5/11 14:20
 * 选择日期时间底部Dialog
 */
class DateTimeChoiceDialogFragment : BaseBottomDialogFragment() {

    override fun dialogId(): Int = R.layout.dialog_date_time_chioce

    override fun initView() {
        choice_hint_tv.text = System.currentTimeMillis().yearAndMonthFormat()
        hour_wheel_view.setAdapter(WheelStringAdapter(resources.getStringArray(R.array.time_type_12).toList()))

    }

    override fun initEvent() {
        calendar_view.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                choice_hint_tv.text = calendar?.timeInMillis?.yearAndMonthFormat()
            }

            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }
        })
    }

    override fun initData() {
    }
}