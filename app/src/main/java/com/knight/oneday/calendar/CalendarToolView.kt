package com.knight.oneday.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.knight.oneday.R
import com.knight.oneday.utilities.currentDayOfMonth
import com.knight.oneday.utilities.singleClick

/**
 * Create by FLJ in 2020/6/16 14:38
 * 日期视图工具View 主要展示当前选择的日期以及回到今天的一个操作
 */
class CalendarToolView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.view_calendar_tool, this, true)
    private val currentDateTv: AppCompatTextView
    private val currentDateIcon: FrameLayout

    var onCurrentDayClicked: OnCurrentDayClicked? = null

    init {
        currentDateTv = contentView.findViewById(R.id.tool_current_date_tv)
        currentDateIcon = contentView.findViewById(R.id.tool_current_date_icon)
        currentDateIcon.findViewById<AppCompatTextView>(R.id.current_date_tv).text =
            currentDayOfMonth().toString()
        currentDateIcon.singleClick {
            onCurrentDayClicked?.onCurrentDayClicked()
        }
    }

    fun changeSelectedDay(selectedDayString: String) {
        currentDateTv.text = selectedDayString
    }

    interface OnCurrentDayClicked {
        fun onCurrentDayClicked()
    }

}