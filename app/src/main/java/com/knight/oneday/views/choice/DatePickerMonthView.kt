package com.knight.oneday.views.choice

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import com.knight.oneday.R
import com.knight.oneday.utilities.UI_CALENDAR_SCHEME_IS_DONE
import com.knight.oneday.utilities.UI_CALENDAR_SCHEME_IS_EXPIRED
import com.knight.oneday.utilities.dp
import com.knight.oneday.utilities.dp2px
import com.knight.oneday.views.themeColor

/**
 * Create by FLJ in 2020/5/11 15:34
 * 月份View
 */
class DatePickerMonthView(context: Context) : MonthView(context) {

    init {
        mSelectedPaint.maskFilter = BlurMaskFilter(10F, BlurMaskFilter.Blur.SOLID)
    }

    override fun onDrawText(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val baselineY = mTextBaseLine + y
        val cx = x + mItemWidth / 2
        when {
            isSelected -> {
                canvas.drawText(
                    calendar.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    mSelectTextPaint
                )
            }
            hasScheme -> {
                canvas.drawText(
                    calendar.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    if (calendar.isCurrentDay) mCurDayTextPaint else if (calendar.isCurrentMonth) mCurMonthTextPaint else mOtherMonthTextPaint
                )
            }
            else -> {
                canvas.drawText(
                    calendar.day.toString(), cx.toFloat(), baselineY,
                    if (calendar.isCurrentDay) mCurDayTextPaint else if (calendar.isCurrentMonth) mCurMonthTextPaint else mOtherMonthTextPaint
                )
            }
        }

    }

    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        val centerX = x.toFloat() + mItemWidth / 2
        val centerY = y.toFloat() + mItemHeight / 2
        canvas.drawCircle(centerX, centerY, mItemWidth / 2 - 4F.dp, mSelectedPaint)
        return false
    }


    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
    }

}