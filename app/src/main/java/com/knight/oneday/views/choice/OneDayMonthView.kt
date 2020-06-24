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
class OneDayMonthView(context: Context) : MonthView(context) {

    private val mSchemeBasicPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val schemeRadius = 2F.dp
    private val schemeCirclePadding = 2F.dp

    init {
        mSelectedPaint.maskFilter = BlurMaskFilter(50F, BlurMaskFilter.Blur.SOLID)
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
        if (hasScheme)
            drawScheme(canvas, calendar, x, y, true)
        return false
    }

    private fun drawScheme(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        isSelected: Boolean = false
    ) {
        val schemeSize = calendar.schemes.size

        val schemeStartCenterX = (x + mItemWidth / 2).toFloat()
        val schemeCenterY = y + mItemHeight - 4 * schemeCirclePadding - 4 * schemeRadius

        when (schemeSize) {
            1 -> {
                prepareSchemePaintColor(calendar.schemes[0].type, isSelected)
                canvas.drawCircle(
                    schemeStartCenterX,
                    schemeCenterY,
                    schemeRadius,
                    mSchemeBasicPaint
                )
            }
            2 -> {
                prepareSchemePaintColor(calendar.schemes[0].type, isSelected)
                canvas.drawCircle(
                    schemeStartCenterX - schemeRadius - schemeCirclePadding / 2,
                    schemeCenterY,
                    schemeRadius, mSchemeBasicPaint
                )
                prepareSchemePaintColor(calendar.schemes[1].type, isSelected)
                canvas.drawCircle(
                    schemeStartCenterX + schemeRadius + schemeCirclePadding / 2,
                    schemeCenterY,
                    schemeRadius,
                    mSchemeBasicPaint
                )
            }
            3 -> {
                prepareSchemePaintColor(calendar.schemes[0].type, isSelected)
                canvas.drawCircle(
                    schemeStartCenterX - 2 * schemeRadius - schemeCirclePadding,
                    schemeCenterY,
                    schemeRadius,
                    mSchemeBasicPaint
                )
                prepareSchemePaintColor(calendar.schemes[1].type, isSelected)
                canvas.drawCircle(
                    schemeStartCenterX,
                    schemeCenterY,
                    schemeRadius,
                    mSchemeBasicPaint
                )
                prepareSchemePaintColor(calendar.schemes[2].type, isSelected)
                canvas.drawCircle(
                    schemeStartCenterX + 2 * schemeRadius + schemeCirclePadding,
                    schemeCenterY,
                    schemeRadius,
                    mSchemeBasicPaint
                )
            }
        }
    }

    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
        drawScheme(canvas, calendar, x, y)
    }

    private fun prepareSchemePaintColor(type: Int, isSelected: Boolean = false) {
        mSchemeBasicPaint.color = when (type) {
            UI_CALENDAR_SCHEME_IS_DONE -> context.themeColor(if (isSelected) R.attr.colorSurface else R.attr.colorFinished)
            UI_CALENDAR_SCHEME_IS_EXPIRED -> context.themeColor(R.attr.colorExpired)
            else -> context.themeColor(R.attr.colorUnFinished)
        }
    }
}