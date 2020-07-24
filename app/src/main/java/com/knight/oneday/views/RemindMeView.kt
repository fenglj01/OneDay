package com.knight.oneday.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Switch
import androidx.constraintlayout.widget.ConstraintLayout
import com.knight.oneday.R

/**
 * Create by FLJ in 2020/7/23 11:03
 * 提醒View
 */
class RemindMeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onRemindStatusChangedListener: OnRemindStatusChangedListener? = null

    private val switch: Switch

    init {
        LayoutInflater.from(context).inflate(R.layout.view_remind_me, this, true).run {
            switch = findViewById<Switch>(R.id.sw_remind_me).apply {
                setOnCheckedChangeListener { _, isChecked ->
                    onRemindStatusChangedListener?.onRemindStatusChanged(isChecked)
                }
            }
        }
    }

    interface OnRemindStatusChangedListener {
        fun onRemindStatusChanged(isRemind: Boolean)
    }

    fun openRemindMe() {
        switch.setOnCheckedChangeListener(null)
    }

    fun neverRemindMe() {
        switch.isChecked = false
    }

}