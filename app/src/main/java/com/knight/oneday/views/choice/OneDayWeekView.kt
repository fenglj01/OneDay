package com.knight.oneday.views.choice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.WeekView
import com.knight.oneday.utilities.dp2px

class OneDayWeekView(context: Context) : WeekView(context) {

    private var rectF: RectF = RectF()
    private val selectedRectConner: Float = dp2px(context, 8F)

    private val mSchemeBasicPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDrawText(
        canvas: Canvas?,
        calendar: Calendar?,
        x: Int,
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

    override fun onDrawScheme(canvas: Canvas?, calendar: Calendar?, x: Int) {
        mSchemeBasicPaint.color = calendar?.schemeColor ?: Color.BLACK
        val schemeList = calendar?.schemes
        schemeList?.run {
            canvas?.drawCircle(0F, 0F, 5F, mSchemeBasicPaint)
        }
    }
}