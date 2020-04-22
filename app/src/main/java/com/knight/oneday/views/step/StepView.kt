package com.knight.oneday.views.step

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import com.knight.oneday.R
import java.lang.RuntimeException

/**
 * Create by FLJ in 2020/4/22 9:22
 * 步骤View
 */
class StepView @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null, private val defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        /* 状态 未完成、完成、正在执行 */
        const val STEP_STATUS_UNFINISHED = 0
        const val STEP_STATUS_FINISHED = 1
        const val STEP_STATUS_EXECUTING = 2
        /* 线风格 实线、虚线 */
        const val LINE_TYPE_SOLID = 0
        const val LINE_TYPE_DOTTED = 1
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(STEP_STATUS_UNFINISHED, STEP_STATUS_FINISHED, STEP_STATUS_EXECUTING)
    annotation class StepStatus

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(LINE_TYPE_SOLID, LINE_TYPE_DOTTED)
    annotation class LineType

    @StepStatus
    var stepStatus: Int = STEP_STATUS_UNFINISHED
    @LineType
    var lineType: Int = LINE_TYPE_SOLID
    var animationDuration: Int = 0
    @Dimension
    var selectedRadius: Int = 0
    @Dimension
    var circleRadius: Int = 0
    @Dimension(unit = Dimension.SP)
    var stepNumberTextSize: Float = 0F
    @Dimension
    var lineHeight: Int = 0
    @Dimension
    var lineMargin: Int = 0
    @Dimension
    var lineLength: Int = 0
    @ColorInt
    var finishedCircleColor: Int = Color.WHITE
    @ColorInt
    var finishedTextColor: Int = Color.BLACK
    @ColorInt
    var finishedMarkColor: Int = Color.WHITE
    @ColorInt
    var finishedLineColor: Int = Color.BLACK
    @ColorInt
    var unfinishedCircleColor: Int = Color.GRAY
    @ColorInt
    var unfinishedTextColor: Int = Color.BLACK
    @ColorInt
    var unfinishedLineColor: Int = Color.BLACK
    @ColorInt
    var executingCircleColor: Int = Color.GRAY
    @ColorInt
    var executingTextColor: Int = Color.GRAY
    @ColorInt
    var executingMarkColor: Int = Color.BLUE
    @ColorInt
    var executingLineColor: Int = Color.BLUE
    var stepNumber = 1

    private lateinit var textPaint: Paint
    private lateinit var paint: Paint
    private lateinit var textBounds: Rect

    private var stepSelected: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    init {
        initAttrs()
        initUtils()
    }

    private fun initAttrs() {

        val typeArray = context!!.obtainStyledAttributes(
            attrs,
            R.styleable.StepView,
            defStyleAttr,
            R.style.StepView
        )

        typeArray.apply {
            stepStatus = getInteger(R.styleable.StepView_svStatus, 0)
            lineType = getInteger(R.styleable.StepView_svLineType, 0)
            animationDuration = getInteger(R.styleable.StepView_svAnimationDuration, 0)
            circleRadius = getDimensionPixelSize(R.styleable.StepView_svCircleRadius, 0)
            finishedCircleColor = getColor(R.styleable.StepView_svFinishedCircleColor, 0)
            finishedTextColor = getColor(R.styleable.StepView_svFinishedTextColor, 0)
            finishedLineColor = getColor(R.styleable.StepView_svFinishedLineColor, 0)
            finishedMarkColor = getColor(R.styleable.StepView_svFinishedMarkColor, 0)
            unfinishedCircleColor = getColor(R.styleable.StepView_svUnfinishedCircleColor, 0)
            unfinishedLineColor = getColor(R.styleable.StepView_svUnfinishedLineColor, 0)
            unfinishedTextColor = getColor(R.styleable.StepView_svUnfinishedTextColor, 0)
            executingCircleColor = getColor(R.styleable.StepView_svExecutingCircleColor, 0)
            executingLineColor = getColor(R.styleable.StepView_svExecutingLineColor, 0)
            executingMarkColor = getColor(R.styleable.StepView_svExecutingMarkColor, 0)
            executingTextColor = getColor(R.styleable.StepView_svExecutingTextColor, 0)
            stepNumberTextSize = getDimension(R.styleable.StepView_svStepNumberTextSize, 0F)
            stepNumber = getInteger(R.styleable.StepView_svStepNumber, 0)
            lineHeight = getDimensionPixelSize(R.styleable.StepView_svLineHeight, 0)
            lineLength = getDimensionPixelSize(R.styleable.StepView_svLineLength, 0)
            lineMargin = getDimensionPixelSize(R.styleable.StepView_svLineMargin, 0)
            selectedRadius = getDimensionPixelSize(R.styleable.StepView_svSelectedRadius, 0)
            stepSelected = getBoolean(R.styleable.StepView_svSelected, false)
            recycle()
        }

    }

    private fun initUtils() {
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = stepNumberTextSize
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        textBounds = Rect()
        textPaint.getTextBounds(
            stepNumber.toString(),
            0,
            stepNumber.toString().length,
            textBounds
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /* 控制分割线的宽度不要草果半径 */
        if (lineHeight > circleRadius) {
            throw RuntimeException("lineHeight > 2 * circleRadius")
        }
        /* 选择外圆环的半径 需要比内容的半径要大 */
        if (selectedRadius < circleRadius) {
            throw  RuntimeException("selectedRadius must be > circleRadius")
        }

        val needLine = stepNumber != 1
        val mCircleWidth = paddingStart + paddingEnd + 2 * selectedRadius
        val mLineNeedWidth = lineMargin + lineLength
        val mWidth = if (needLine) mCircleWidth + mLineNeedWidth else mCircleWidth
        val mHeight = paddingTop + paddingBottom + 2 * selectedRadius

        setMeasuredDimension(mWidth, mHeight)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return
        canvas?.run {
            drawStepCircle()
            drawStepLine()
            drawStepText()
        }
    }

    private fun Canvas.drawStepLine() {
        if (stepNumber == 0) return
        prepareLineColor()
        paint.strokeWidth = lineHeight.toFloat()
        val startX = paddingStart + 2 * selectedRadius + lineMargin
        val startY = height / 2
        drawLine(
            startX.toFloat(),
            startY.toFloat(),
            (startX + lineLength).toFloat(),
            startY.toFloat(),
            paint
        )
    }

    private fun Canvas.drawStepCircle() {
        prepareCircleColor()
        val cx = (width - lineMargin - lineLength) / 2
        val cy = height / 2
        drawCircle(
            cx.toFloat(),
            cy.toFloat(),
            circleRadius.toFloat(),
            paint
        )
    }

    private fun Canvas.drawStepText() {
        prepareTextColor()
        val textX = paddingStart + selectedRadius - textBounds.width() / 2
        val textY = paddingTop + selectedRadius - textBounds.height() / 2 - textBounds.bottom
        drawText(stepNumber.toString(), textX.toFloat(), textY.toFloat(), textPaint)
    }

    private fun prepareLineColor() {
        paint.apply {
            color = when (stepStatus) {
                STEP_STATUS_UNFINISHED -> unfinishedLineColor
                STEP_STATUS_FINISHED -> finishedLineColor
                else -> executingLineColor
            }
        }
    }

    private fun prepareCircleColor() {
        paint.apply {
            color = when (stepStatus) {
                STEP_STATUS_UNFINISHED -> unfinishedCircleColor
                STEP_STATUS_FINISHED -> finishedCircleColor
                else -> executingCircleColor
            }
        }
    }

    private fun prepareTextColor() {
        textPaint.apply {
            color = when (stepStatus) {
                STEP_STATUS_UNFINISHED -> unfinishedTextColor
                STEP_STATUS_FINISHED -> finishedTextColor
                else -> executingTextColor
            }
        }
    }


    /* 选中状态切换 */
    fun toggleSelected() {
        stepSelected = !stepSelected
    }

    fun finishStep() {

    }

    fun unFinishStep() {

    }

    fun executeStep() {

    }

}
