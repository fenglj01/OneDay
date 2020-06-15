package com.knight.oneday.views.dialog

import com.knight.oneday.R
import com.knight.oneday.utilities.singleClick
import kotlinx.android.synthetic.main.dialog_time_picker.*

/**
 * Create by FLJ in 2020/6/15 9:05
 * 时间选择
 */
class TimePickerDialog(private val listener: TimePickerListener) : BaseBottomDialogFragment() {

    private var pickHourOfDay: Int = 0
    private var pickMinute: Int = 0

    override fun dialogId(): Int = R.layout.dialog_time_picker

    override fun initView() {
        time_picker.setIs24HourView(true)
        pickHourOfDay = time_picker.hour
        pickMinute = time_picker.minute
    }

    override fun initEvent() {
        time_picker.setOnTimeChangedListener { _, hourOfDay, minute ->
            listener.onTimeChanged(hourOfDay, minute)
            pickHourOfDay = hourOfDay
            pickMinute = minute
        }
        time_picker_sure_btn.singleClick {
            listener.onTimeConfirm(pickHourOfDay, pickMinute)
            dialog?.dismiss()
        }
        time_picker_cancel_btn.singleClick {
            listener.onTimeCancel()
            dialog?.dismiss()
        }

    }

    override fun initData() {
    }

    interface TimePickerListener {
        fun onTimeChanged(hourOfDay: Int, minute: Int)
        fun onTimeConfirm(hourOfDay: Int, minute: Int)
        fun onTimeCancel()
    }

    abstract class TimePickerConfirmListener : TimePickerListener {
        override fun onTimeChanged(hourOfDay: Int, minute: Int) {
        }

        override fun onTimeCancel() {
        }
    }
}