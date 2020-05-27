package com.knight.oneday.views.hsv

import android.content.Context
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

/**
 * Create by FLJ in 2020/5/27 9:26
 * 步骤指示器
 */
class StepIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


}

/* 回执步骤内容的分别封装 */
data class StepItem(
    val lineItem: LineItem?,
    val circleItem: CircleItem,
    val contentItem: ContentItem
)

/* 步骤分割线 */
data class LineItem(val start: Float, val end: Float, val paint: Paint)

/* 可绘制图片 在完成和正在执行的时候用图片进行绘制 */
data class DrawableItem(val rect: Rect, val drawable: Drawable)

/* 步骤中心圆 包括了填充和描边 */
data class CircleItem(
    val center: PointF,
    val radius: Float,
    val stokePaint: Paint,
    val fillPaint: Paint
)

/* 步骤的核心内容 可能是步骤数字 也可能是图片 */
data class ContentItem(
    val number: Int,
    val x: Float = 0.0F,
    val y: Float = 0.0F,
    val drawableItem: DrawableItem? = null
)