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
import com.knight.oneday.R
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
        const val CIRCLE_TYPE_FILL = 0
        const val CIRCLE_TYPE_FILL_STOKE = 1
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

    var circleStyle: Int by OnLayoutProp(CIRCLE_TYPE_FILL) {
        initCirclePaints()
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

        val typeArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.HorizontalStepView, 0, 0)
        /* 参数初始化 */
        with(typeArray) {
            stepCount = getInt(R.styleable.HorizontalStepView_hsvStepCount, stepCount)
            currentCount = getInt(
                R.styleable.HorizontalStepView_hsvCurrentCount,
                INVALID_STATUS_COUNT
            )
            circleRadius =
                getDimension(R.styleable.HorizontalStepView_hsvCircleRadius, circleRadius)
            finishedDrawable = getDrawable(R.styleable.HorizontalStepView_hsvFinishedDrawable)
            executingDrawable = getDrawable(R.styleable.HorizontalStepView_hsvExecutingDrawable)
            circleStokeWidth =
                getDimension(R.styleable.HorizontalStepView_hsvCircleStokeWidth, circleStokeWidth)
            circleFillColor =
                getColor(R.styleable.HorizontalStepView_hsvCircleFillColor, circleFillColor)
            circleFillFinishedColor = getColor(
                R.styleable.HorizontalStepView_hsvCircleFillFinishedColor,
                circleFillFinishedColor
            )
            circleFillExecutingColor = getColor(
                R.styleable.HorizontalStepView_hsvCircleFillExecutingColor,
                circleFillExecutingColor
            )
            circleStokeColor =
                getColor(R.styleable.HorizontalStepView_hsvCircleStokeColor, circleStokeColor)
            circleStokeFinishedColor = getColor(
                R.styleable.HorizontalStepView_hsvCircleStokeFinishedColor,
                circleStokeFinishedColor
            )
            circleStokeExecutingColor =
                getColor(
                    R.styleable.HorizontalStepView_hsvCircleStokeExecutingColor,
                    circleStokeExecutingColor
                )
            lineLength = getDimension(R.styleable.HorizontalStepView_hsvLineLength, lineLength)
            lineHeight = getDimension(R.styleable.HorizontalStepView_hsvLineHeight, lineHeight)
            lineGap = getDimension(R.styleable.HorizontalStepView_hsvLineGap, lineGap)
            lineColor = getColor(R.styleable.HorizontalStepView_hsvLineColor, lineColor)
            lineFinishedColor =
                getColor(R.styleable.HorizontalStepView_hsvLineFinishedColor, lineFinishedColor)
            lineExecutingColor =
                getColor(R.styleable.HorizontalStepView_hsvLineExecutingColor, lineExecutingColor)
            contentTextColor =
                getColor(R.styleable.HorizontalStepView_hsvTextColor, contentTextColor)
            contentTextSize =
                getDimension(R.styleable.HorizontalStepView_hsvTextSize, contentTextSize)
            circleStyle = getInt(R.styleable.HorizontalStepView_hsvStyle, circleStyle)
            recycle()
        }

        initCirclePaints()

        initLinePaints()

        propsInitializedOnce = true
    }

    private fun initLinePaints() {

        linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        linePaint.apply {
            style = Paint.Style.STROKE
            color = lineColor
            strokeWidth = lineHeight
        }

        lineFinishedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        lineFinishedPaint.apply {
            style = Paint.Style.STROKE
            color = lineFinishedColor
            strokeWidth = lineHeight
        }

        lineExecutingPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        lineExecutingPaint.apply {
            style = Paint.Style.STROKE
            color = lineExecutingColor
            strokeWidth = lineHeight
        }

    }


    /**
     * 初始化中心圆的画笔
     */
    private fun initCirclePaints() {

        if (isShowingExecutingStatus()) {
            if (circleFillExecutingPaint == null) {
                circleFillExecutingPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                circleFillExecutingPaint?.apply {
                    style = Paint.Style.FILL
                    color = circleFillExecutingColor
                }
            }
        }

        if (isShowingUnFinishedStatus()) {
            if (circleFillPaint == null) {
                circleFillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                circleFillPaint?.apply {
                    style = Paint.Style.FILL
                    color = circleFillColor
                }
            }
        }

        if (isShowingFinishedStatus()) {
            if (circleFillFinishedPaint == null) {
                circleFillFinishedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                circleFillFinishedPaint?.apply {
                    style = Paint.Style.FILL
                    color = circleFillFinishedColor
                }
            }
        }

        /* 如果风格是描边加填充那么需要对其初始化 */
        if (circleStyle == CIRCLE_TYPE_FILL_STOKE) {

            if (isShowingFinishedStatus()) {
                if (circleStokeFinishedPaint == null) {
                    circleStokeFinishedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    circleStokeFinishedPaint?.apply {
                        style = Paint.Style.STROKE
                        color = circleStokeFinishedColor
                        strokeWidth = circleStokeWidth
                    }
                }
            }

            if (isShowingExecutingStatus()) {
                if (circleStokeExecutingPaint == null) {
                    circleStokeExecutingPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    circleStokeExecutingPaint?.apply {
                        style = Paint.Style.STROKE
                        color = circleStokeExecutingColor
                        strokeWidth = circleStokeWidth
                    }
                }
            }

            if (isShowingUnFinishedStatus()) {
                if (circleStokePaint == null) {
                    circleStokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    circleStokePaint?.apply {
                        style = Paint.Style.STROKE
                        color = circleStokeColor
                        strokeWidth = circleStokeWidth
                    }
                }
            }

        }

    }

    /* 是否有正在执行项需要展示 currentCount 在步骤数内说明有展示的 */
    private fun isShowingExecutingStatus() = currentCount in 1..stepCount

    /* 是否有未完成项目需要展示 currentCount 在步骤数内切不是最后一项代表 有展示的 */
    private fun isShowingUnFinishedStatus() = currentCount in 0 until stepCount

    /* 是否显示了已完成的项目 */
    private fun isShowingFinishedStatus() = currentCount != INVALID_STATUS_COUNT

    /**
     * 初始化绘制的内容
     */
    private fun setDrawingData() {

    }

    override fun getSuggestedMinimumWidth(): Int {
        /* 没有数据 */
        if (stepCount == 0) return 0

        val lineLengthComputed = lineLength
        /* 所有步骤圆所需要的宽度 注意strokeWidth 绘制时只有一半 */
        val totalCircleLength = stepCount * (2 * (circleRadius + circleStokeWidth / 2))
        /* line的个数比圆少一个 */
        val totalLineLength = (stepCount - 1) * (lineLengthComputed + (lineGap * 2))

        return (totalCircleLength + totalLineLength).toInt()
    }

    override fun getSuggestedMinimumHeight(): Int {

        if (stepCount == 0) return 0

        val totalHeight = 2 * circleRadius + circleStokeWidth

        return totalHeight.toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
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