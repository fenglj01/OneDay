package com.knight.oneday.views.choice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import com.knight.oneday.utilities.dp2px
import com.knight.oneday.utilities.sp

/**
 * Create by FLJ in 2020/5/11 15:34
 * 月份View
 */
class OneDayMonthView(context: Context) : MonthView(context) {

    private var rectF: RectF = RectF()
    private val selectedRectConner: Float = dp2px(context, 8F)

    override fun onDrawText(
        canvas: Canvas?,
        calendar: Calendar?,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val baselineY = mTextBaseLine + y
        val cx = x + mItemWidth / 2
        when {
            isSelected -> {
                canvas!!.drawText(
                    calendar!!.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    mSelectTextPaint
                )
            }
            hasScheme -> {
                canvas!!.drawText(
                    calendar!!.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    if (calendar.isCurrentDay) mCurDayTextPaint else if (calendar.isCurrentMonth) mSchemeTextPaint else mOtherMonthTextPaint
                )
            }
            else -> {
                canvas!!.drawText(
                    calendar!!.day.toString(), cx.toFloat(), baselineY,
                    if (calendar.isCurrentDay) mCurDayTextPaint else if (calendar.isCurrentMonth) mCurMonthTextPaint else mOtherMonthTextPaint
                )
            }
        }

    }

    override fun onDrawSelected(
        canvas: Canvas?,
        calendar: Calendar?,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        rectF.apply {
            left = x.toFloat()
            right = (x + mItemWidth).toFloat()
            top = y.toFloat()
            bottom = (y + mItemHeight).toFloat()
        }
        canvas?.drawRoundRect(rectF, selectedRectConner, selectedRectConner, mSelectedPaint)
        return false
    }

    override fun onDrawScheme(canvas: Canvas?, calendar: Calendar?, x: Int, y: Int) {
    }
}