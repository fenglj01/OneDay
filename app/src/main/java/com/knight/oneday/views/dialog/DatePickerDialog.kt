package com.knight.oneday.views.dialog

import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.knight.oneday.R
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

    override fun dialogId(): Int = R.layout.dialog_date_picker

    override fun initView() {
    }

    override fun initEvent() {
        pickTimeInMills = date_view.selectedCalendar.timeInMillis

        current_date.onClickListener = {
            date_view.scrollToCurrent()
        }

        date_picker_sure_btn.singleClick {
            onDatePickerListener?.onDateConfirm(pickTimeInMills)
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
                    onDatePickerListener?.onDateChange(timeInMillis)
                }
            }

            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }
        })
    }

    override fun initData() {
    }

    private fun dis() {
        if (dialog?.isShowing == true) dialog?.dismiss()
    }

    interface OnDatePickerListener {
        fun onDateChange(timeInMills: Long)
        fun onDateConfirm(timeInMills: Long)
        fun onDateCancel()
    }

    abstract class OnDatePickerConfirmListener : OnDatePickerListener {
        override fun onDateCancel() {
        }

        override fun onDateChange(timeInMills: Long) {
        }
    }

}