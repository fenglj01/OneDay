package com.knight.oneday.views.dialog

import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
import com.knight.oneday.add.AddTaskFragment.Companion.ARG_SELECTED_CALENDAR
import com.knight.oneday.add.AddTaskFragment.Companion.TAG_TIME_PICKER_DIALOG
import com.knight.oneday.utilities.SysCalendar
import com.knight.oneday.utilities.currentTimeMills
import com.knight.oneday.utilities.singleClick
import kotlinx.android.synthetic.main.dialog_date_picker.*

/**
 * Create by FLJ in 2020/6/15 9:29
 * 日期选择
 */
class DatePickerDialog(private var onDatePickerListener: OnDatePickerListener?) :
    BaseBottomDialogFragment() {

    private var pickTimeInMills = currentTimeMills()
    private var pickYear: Int = 0
    private var pickMonth: Int = 0
    private var pickDay: Int = 0
    private var initCalendar: SysCalendar? = null

    override fun dialogId(): Int = R.layout.dialog_date_picker

    override fun initView() {
        initCalendar?.let {
            date_view.scrollToCalendar(
                it[SysCalendar.YEAR],
                it[SysCalendar.MONTH] + 1,
                it[SysCalendar.DAY_OF_MONTH]
            )
        }
    }

    override fun initEvent() {
        pickTimeInMills = date_view.selectedCalendar.timeInMillis
        pickYear = date_view.curYear
        pickMonth = date_view.curMonth
        pickDay = date_view.curDay

        current_date.onClickListener = {
            date_view.scrollToCurrent()
        }

        date_picker_sure_btn.singleClick {
            onDatePickerListener?.onDateConfirm(pickTimeInMills, pickYear, pickMonth, pickDay)
            dis()
        }

        date_picker_cancel_btn.singleClick {
            onDatePickerListener?.onDateCancel()
            dis()
        }

        date_view.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                calendar?.run {
                    pickTimeInMills = timeInMillis
                    pickYear = year
                    pickMonth = month
                    pickDay = day
                    onDatePickerListener?.onDateChange(timeInMillis, year, month, day)
                }
            }

            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }
        })
    }

    override fun initData() {
        initCalendar = arguments?.getSerializable(ARG_SELECTED_CALENDAR) as? SysCalendar
    }


    private fun dis() {
        if (dialog?.isShowing == true) dialog?.dismiss()
    }

    interface OnDatePickerListener {
        fun onDateChange(timeInMills: Long, year: Int, month: Int, day: Int)
        fun onDateConfirm(timeInMills: Long, year: Int, month: Int, day: Int)
        fun onDateCancel()
    }

    abstract class OnDatePickerConfirmListener : OnDatePickerListener {
        override fun onDateCancel() {
        }

        override fun onDateChange(timeInMills: Long, year: Int, month: Int, day: Int) {
        }
    }

}