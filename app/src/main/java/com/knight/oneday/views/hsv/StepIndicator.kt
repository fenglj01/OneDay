package com.knight.oneday.views.hsv

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.knight.oneday.utilities.px
import com.knight.oneday.utilities.sp
import kotlin.reflect.KProperty

/**
 * Create by FLJ in 2020/5/27 9:26
 * 步骤指示器
 */
class StepIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val INVALID_STATUS_COUNT = -1
    }

    /* 属性是否都完成了一次初始化 注意:因为是绘制多个 所以有些属性会导致整个View大小变化 需要requestLayout 否则会导致限制不全的问题 */
    private var propsInitializedOnce: Boolean = false
    /* 绘制内容集合 */
    private val drawingData: MutableList<StepItem> = mutableListOf()

    /* 步骤的总数 */
    var stepCount: Int by OnLayoutProp(0)
    /* 当前的步骤数 如果在读取过attrs后更改这个属性 那么需要requestLayout */
    var currentCount: Int by OnLayoutProp(INVALID_STATUS_COUNT) {
        initCirclePaints()
    }
    var circleRadius: Float by OnLayoutProp(16F.px)
    /* drawable的修改需要触发重绘 */
    var finishedDrawable: Drawable? by OnValidateProp(null) {
        setDrawingData()
    }
    var executingDrawable: Drawable? by OnValidateProp(null) {
        setDrawingData()
    }
    var circleStokeWidth: Float by OnLayoutProp(2F.px)
    /* circle colors */
    var circleFillColor: Int by OnValidateProp(Color.BLACK)
    var circleFillFinishedColor: Int by OnValidateProp(Color.BLACK)
    var circleFillExecutingColor: Int by OnValidateProp(Color.BLACK)
    var circleStokeColor: Int by OnValidateProp(Color.BLACK)
    var circleStokeFinishedColor: Int by OnValidateProp(Color.BLACK)
    var circleStokeExecutingColor: Int by OnValidateProp(Color.BLACK)
    /* line */
    var lineLength: Float by OnLayoutProp(30F.px)
    var lineHeight: Float by OnValidateProp(2F.px) {
        linePaint.strokeWidth = lineHeight
        lineFinishedPaint.strokeWidth = lineHeight
        lineExecutingPaint.strokeWidth = lineHeight
    }
    var lineGap: Float by OnLayoutProp(2F.px)
    var lineColor: Int by OnValidateProp(Color.BLACK) {
        linePaint.color = lineColor
    }
    var lineFinishedColor: Int by OnValidateProp(Color.BLACK) {
        lineFinishedPaint.color = lineFinishedColor
    }
    var lineExecutingColor: Int by OnValidateProp(Color.BLACK) {
        lineExecutingPaint.color = lineExecutingColor
    }
    /* text */
    var contentTextColor: Int by OnValidateProp(Color.WHITE) {
        textPaint.color = contentTextColor
    }
    var contentTextSize: Float by OnValidateProp(12f.sp) {
        textPaint.textSize = contentTextSize
    }

    private var circleStokePaint: Paint? = null
    private var circleStokeFinishedPaint: Paint? = null
    private var circleStokeExecutingPaint: Paint? = null

    private var circleFillPaint: Paint? = null
    private var circleFillFinishedPaint: Paint? = null
    private var circleFillExecutingPaint: Paint? = null

    private lateinit var linePaint: Paint
    private lateinit var lineFinishedPaint: Paint
    private lateinit var lineExecutingPaint: Paint
    private lateinit var textPaint: TextPaint

    init {

    }

    /**
     * 初始化中心圆的画笔
     */
    private fun initCirclePaints() {

    }

    /**
     * 初始化绘制的内容
     */
    private fun setDrawingData() {

    }


    /* 属性代理 可能会需要requestLayout的属性 */
    inner class OnLayoutProp<T>(private var field: T, private inline var func: () -> Unit = {}) {
        operator fun setValue(thisRef: Any?, p: KProperty<*>, v: T) {
            field = v
            if (propsInitializedOnce) {
                drawingData.clear()
                func()
                requestLayout()
            }

        }

        operator fun getValue(thisRef: Any?, p: KProperty<*>): T {
            return field
        }

    }

    /**
     * 属性代理 需要进行 invalidate的属性
     */
    inner class OnValidateProp<T>(private var field: T, private inline var func: () -> Unit = {}) {
        operator fun setValue(thisRef: Any?, p: KProperty<*>, v: T) {
            field = v
            if (propsInitializedOnce) {
                func()
                invalidate()
            }
        }

        operator fun getValue(thisRef: Any?, p: KProperty<*>): T {
            return field
        }

    }
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