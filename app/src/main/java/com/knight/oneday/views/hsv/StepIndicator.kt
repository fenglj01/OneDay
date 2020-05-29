package com.knight.oneday.views.hsv

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
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
    var circleStrokeWidth: Float by OnLayoutProp(2F.px)
    /* circle colors */
    var circleFillColor: Int by OnValidateProp(Color.BLACK)
    var circleFillFinishedColor: Int by OnValidateProp(Color.BLACK)
    var circleFillExecutingColor: Int by OnValidateProp(Color.BLACK)
    var circleStrokeColor: Int by OnValidateProp(Color.BLACK)
    var circleStrokeFinishedColor: Int by OnValidateProp(Color.BLACK)
    var circleStrokeExecutingColor: Int by OnValidateProp(Color.BLACK)
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

    private var circleStrokePaint: Paint? = null
    private var circleStrokeFinishedPaint: Paint? = null
    private var circleStrokeExecutingPaint: Paint? = null

    private var circleFillPaint: Paint? = null
    private var circleFillFinishedPaint: Paint? = null
    private var circleFillExecutingPaint: Paint? = null

    private lateinit var linePaint: Paint
    private lateinit var lineFinishedPaint: Paint
    private lateinit var lineExecutingPaint: Paint
    private lateinit var textPaint: TextPaint

    init {

        val typeArray =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.HorizontalStepView,
                defStyleAttr,
                R.style.HorizontalStepView
            )
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
            circleStrokeWidth =
                getDimension(R.styleable.HorizontalStepView_hsvCircleStokeWidth, circleStrokeWidth)
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
            circleStrokeColor =
                getColor(R.styleable.HorizontalStepView_hsvCircleStokeColor, circleStrokeColor)
            circleStrokeFinishedColor = getColor(
                R.styleable.HorizontalStepView_hsvCircleStokeFinishedColor,
                circleStrokeFinishedColor
            )
            circleStrokeExecutingColor =
                getColor(
                    R.styleable.HorizontalStepView_hsvCircleStokeExecutingColor,
                    circleStrokeExecutingColor
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
                if (circleStrokeFinishedPaint == null) {
                    circleStrokeFinishedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    circleStrokeFinishedPaint?.apply {
                        style = Paint.Style.STROKE
                        color = circleStrokeFinishedColor
                        strokeWidth = circleStrokeWidth
                    }
                }
            }

            if (isShowingExecutingStatus()) {
                if (circleStrokeExecutingPaint == null) {
                    circleStrokeExecutingPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    circleStrokeExecutingPaint?.apply {
                        style = Paint.Style.STROKE
                        color = circleStrokeExecutingColor
                        strokeWidth = circleStrokeWidth
                    }
                }
            }

            if (isShowingUnFinishedStatus()) {
                if (circleStrokePaint == null) {
                    circleStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    circleStrokePaint?.apply {
                        style = Paint.Style.STROKE
                        color = circleStrokeColor
                        strokeWidth = circleStrokeWidth
                    }
                }
            }

            textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
            textPaint.apply {
                textSize = contentTextSize
                color = contentTextColor
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
        val lastPoint = PointF()
        lastPoint.x = paddingLeft.toFloat() + (circleStrokeWidth / 2)
        lastPoint.y = paddingTop.toFloat() + (circleRadius + (circleStrokeWidth / 2))

        for (pos in 0 until stepCount) {
            var circleStrokePaint: Paint?
            var circleFillPaint: Paint?
            var textPaint: TextPaint?
            var linePaint: Paint?
            var itemDrawable: Drawable?

            var circleRadius = this.circleRadius

            when {
                /* 正在执行项目 绘制内容为 icon 圆 线*/
                isShowingExecutingStatus() && pos == (currentCount - 1) -> {
                    itemDrawable = this.executingDrawable
                    circleFillPaint = this.circleFillExecutingPaint
                    circleStrokePaint = this.circleStrokeExecutingPaint
                    textPaint = null
                    linePaint = this.lineExecutingPaint
                }
                /* 未完成项 绘制内容为 stepNumber 圆 线 */
                isShowingUnFinishedStatus() && pos in (currentCount..stepCount) -> {
                    itemDrawable = null
                    circleFillPaint = this.circleFillPaint
                    circleStrokePaint = this.circleStrokePaint
                    textPaint = this.textPaint
                    linePaint = this.linePaint
                }
                /* 完成项 绘制内容为icon 圆 线 */
                else -> {
                    itemDrawable = this.finishedDrawable
                    circleFillPaint = this.circleFillFinishedPaint
                    circleStrokePaint = this.circleStrokeFinishedPaint
                    textPaint = null
                    linePaint = this.lineFinishedPaint
                }
            }

            /* 圆 */
            val circleItem = CircleItem(
                PointF(lastPoint.x + circleRadius, lastPoint.y),
                circleRadius,
                circleStrokePaint,
                circleFillPaint
            )
            /* 计算下一个起始点 */
            lastPoint.x += (circleRadius * 2F) + circleStrokeWidth / 2
            /* 步骤分割线 */
            var lineItem: LineItem? = null
            /* 因为取不到最后一项 只用判断不是第一个就可以了*/
            if (pos != stepCount - 1) {
                lastPoint.x += lineGap
                lineItem = LineItem(
                    PointF(lastPoint.x, lastPoint.y),
                    PointF(lastPoint.x + lineLength, lastPoint.y),
                    linePaint
                )
                /* 计算下一个圆得起点 */
                lastPoint.x = lineItem.end.x + lineGap + (circleStrokeWidth / 2)
            }

            var drawableItem: DrawableItem? = null
            var contentItem: ContentItem? = null
            /* icon */
            itemDrawable?.run {
                val w = intrinsicWidth
                val h = intrinsicHeight
                val xPos = circleItem.center.x.toInt()
                val yPos = circleItem.center.y.toInt()
                val drawableRect = Rect(
                    xPos - w / 2,
                    yPos - height / 2,
                    xPos + w / 2,
                    yPos + h / 2
                )
                drawableItem = DrawableItem(drawableRect, this)
                contentItem = ContentItem(
                    number = pos + 1,
                    drawableItem = drawableItem
                )
            } ?: run {
                val numberText = (pos + 1).toString()
                val numberRect = Rect()
                textPaint?.getTextBounds(numberText, 0, numberText.length, numberRect)
                val textX = circleItem.center.x - numberRect.width() / 2
                val textY = circleItem.center.y + numberRect.height() / 2
                contentItem = ContentItem(
                    number = pos + 1,
                    x = textX,
                    y = textY,
                    paint = textPaint
                )
            }


            /* 添加到集合中去 */
            drawingData.add(
                StepItem(lineItem, circleItem, contentItem)
            )

        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        setDrawingData()
    }

    override fun getSuggestedMinimumWidth(): Int {
        /* 没有数据 */
        if (stepCount == 0) return 0

        val lineLengthComputed = lineLength
        /* 所有步骤圆所需要的宽度 注意strokeWidth 绘制时只有一半 */
        val totalCircleLength = stepCount * (2 * (circleRadius + circleStrokeWidth / 2))
        /* line的个数比圆少一个 */
        val totalLineLength = (stepCount - 1) * (lineLengthComputed + (lineGap * 2))

        return (totalCircleLength + totalLineLength).toInt()
    }

    override fun getSuggestedMinimumHeight(): Int {

        if (stepCount == 0) return 0

        val totalHeight = 2 * circleRadius + circleStrokeWidth

        return totalHeight.toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = paddingLeft + paddingRight + suggestedMinimumWidth
        val desiredHeight = paddingTop + paddingBottom + suggestedMinimumHeight

        val measuredWidth = resolveSize(desiredWidth, widthMeasureSpec)
        val measuredHeight = resolveSize(desiredHeight, heightMeasureSpec)

        super.onMeasure(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (item in drawingData) {
            /* 绘制圆环相关 */
            item.circleItem.run {
                if (fillPaint != null) {
                    canvas?.drawCircle(center.x, center.y, radius, fillPaint)
                }

                if (strokePaint != null) {
                    canvas?.drawCircle(center.x, center.y, radius, strokePaint)
                }
            }
            /* 绘制文字或者icon */
            item.contentItem?.run {
                if (drawableItem != null) {

                } else {
                    paint?.let {
                        canvas?.drawText(number.toString(), x, y, it)
                    }
                }
            }
            /* 绘制线 */
            item.lineItem?.run {
                canvas?.drawLine(
                    start.x,
                    start.y,
                    end.x,
                    end.y,
                    linePaint
                )
            }

        }
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
    val contentItem: ContentItem?
)

/* 步骤分割线 */
data class LineItem(val start: PointF, val end: PointF, val paint: Paint)

/* 可绘制图片 在完成和正在执行的时候用图片进行绘制 */
data class DrawableItem(val rect: Rect, val drawable: Drawable)

/* 步骤中心圆 包括了填充和描边 */
data class CircleItem(
    val center: PointF,
    val radius: Float,
    val strokePaint: Paint?,
    val fillPaint: Paint?
)

/* 步骤的核心内容 可能是步骤数字 也可能是图片 */
data class ContentItem(
    val number: Int,
    val x: Float = 0.0F,
    val y: Float = 0.0F,
    val drawableItem: DrawableItem? = null,
    val paint: TextPaint? = null
)