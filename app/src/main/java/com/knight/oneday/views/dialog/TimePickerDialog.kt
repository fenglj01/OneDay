package com.knight.oneday.views.dialog

import com.knight.oneday.R
import com.knight.oneday.views.BaseBottomDialogFragment
import kotlinx.android.synthetic.main.dialog_time_picker.*

class TimePickerDialog : BaseBottomDialogFragment() {
    override fun dialogId(): Int = R.layout.dialog_time_picker

    override fun initView() {
        time_picker.setIs24HourView(true)
    }

    override fun initEvent() {
    }

    override fun initData() {
    }
}