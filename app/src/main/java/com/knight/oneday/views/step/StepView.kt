package com.knight.oneday.views.step

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import androidx.core.animation.addListener
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
        set(value) {
            field = value
            invalidate()
        }
    @Dimension(unit = Dimension.SP)
    var stepNumberTextSize: Float = 0F
    @Dimension
    var lineHeight: Int = 0
    @Dimension
    var lineMargin: Int = 0
    @Dimension
    var lineLength: Int = 0
    @Dimension
    var selectedStrokeWidth: Int = 0
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
    @ColorInt
    var selectedColor: Int = 0
    var stepNumber = 1
    var lineAnimatorLength: Int = 0

    private lateinit var textPaint: Paint
    private lateinit var paint: Paint
    private lateinit var textBounds: Rect
    /* 关于选中、取消的动画 */
    private lateinit var selectedAnimator: ObjectAnimator
    private lateinit var unSelectedAnimator: ObjectAnimator
    /* 状态切换动画 */
    private lateinit var stateChangeAnimator: ObjectAnimator
    private var toStatus: Int = STEP_STATUS_UNFINISHED

    var stepViewListener: StepViewListener? = null

    private var stepSelected: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    init {
        initAttrs()
        initUtils()
        initAnimator()
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
            selectedColor = getColor(R.styleable.StepView_svSelectedColor, 0)
            selectedStrokeWidth =
                getDimensionPixelSize(R.styleable.StepView_svSelectedStrokeWidth, 0)
            toStatus = stepStatus
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
        val mLineNeedWidth = 2 * lineMargin + lineLength
        val mWidth = mCircleWidth + mLineNeedWidth
        val mHeight = paddingTop + paddingBottom + 2 * selectedRadius + 2 * selectedStrokeWidth

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
        if (stepNumber == 1) return
        prepareLineColor()
        paint.strokeWidth = lineHeight.toFloat()
        val startX = paddingStart.toFloat()
        val startY = (height / 2).toFloat()
        drawLine(
            startX,
            startY,
            (startX + lineLength),
            startY,
            paint
        )
        /* 如果是在状态切换中 */
        if (isStatusChange()) {
            prepareLineColorByStateChange()
            val startSX =
                (if (toStatus != STEP_STATUS_UNFINISHED) paddingStart else paddingStart + lineLength).toFloat()
            val endX =
                if (toStatus != STEP_STATUS_UNFINISHED) startX + lineAnimatorLength else (paddingStart + lineLength - lineAnimatorLength).toFloat()
            drawLine(
                startSX,
                startY,
                endX,
                startY,
                paint
            )
        }
    }

    private fun Canvas.drawStepCircle() {
        prepareCircleColor()
        val cx = (paddingStart + lineLength + lineMargin + selectedRadius).toFloat()
        val cy = (height / 2).toFloat()
        drawCircle(
            cx,
            cy,
            circleRadius.toFloat(),
            paint
        )
        if (stepSelected) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = selectedStrokeWidth.toFloat()
            /*paint.color = selectedColor*/
            drawCircle(
                cx,
                cy,
                selectedRadius.toFloat(),
                paint
            )
        }
    }

    private fun Canvas.drawStepText() {
        prepareTextColor()
        val textX = paddingStart + lineLength + lineMargin + selectedRadius - textPaint.measureText(
            stepNumber.toString()
        ) / 2
        val textY = (height / 2 + textBounds.height() / 2 - textBounds.bottom).toFloat()

        drawText(stepNumber.toString(), textX, textY, textPaint)
    }

    private fun prepareLineColor() {
        paint.apply {
            paint.style = Paint.Style.FILL
            color = when (stepStatus) {
                STEP_STATUS_UNFINISHED -> unfinishedLineColor
                STEP_STATUS_FINISHED -> finishedLineColor
                else -> executingLineColor
            }
        }
    }

    private fun prepareLineColorByStateChange() {
        paint.apply {
            paint.style = Paint.Style.FILL
            color = when (toStatus) {
                STEP_STATUS_UNFINISHED -> unfinishedLineColor
                STEP_STATUS_FINISHED -> finishedLineColor
                else -> executingLineColor
            }
        }
    }

    private fun prepareCircleColor() {
        paint.apply {
            paint.style = Paint.Style.FILL
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
        if (!stepSelected) {
            selectedAnimator.start()
        } else {
            unSelectedAnimator.start()
        }
    }

    fun cancelSelected() {
        if (stepSelected) {
            unSelectedAnimator.start()
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun initAnimator() {
        val sr = selectedRadius
        var cr = circleRadius

        selectedAnimator = ObjectAnimator.ofInt(this, "circleRadius", cr, sr, cr)
        selectedAnimator.duration = animationDuration.toLong()
        unSelectedAnimator = ObjectAnimator.ofInt(this, "circleRadius", sr, cr)
        unSelectedAnimator.duration = animationDuration.toLong()
        stateChangeAnimator = ObjectAnimator.ofInt(this, "circleRadius", cr, sr, cr)

        stateChangeAnimator.addUpdateListener { animator ->
            // 根据圆的动画 同步到line上来
            lineAnimatorLength = (animator.animatedFraction * lineLength).toInt()
        }
        stateChangeAnimator.addListener(
            onEnd = {
                stepViewListener?.onStatusChangeListener(stepStatus, toStatus)
                this.stepStatus = toStatus
            }
        )
        selectedAnimator.addListener(
            onEnd = {
                stepSelected = !stepSelected
                stepViewListener?.onSelectedStateChangeListener(stepSelected)
            }
        )
        unSelectedAnimator.addListener(
            onStart = {
                stepSelected = !stepSelected
                stepViewListener?.onSelectedStateChangeListener(stepSelected)
            }
        )
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            val eventXRange =
                (width - paddingStart - lineLength).toFloat()..width.toFloat()
            val eventYRange = (0F..height.toFloat())
            if (action == MotionEvent.ACTION_DOWN) {
                if (x in eventXRange && y in eventYRange) {
                    toggleSelected()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 修改StepView状态
     * @param toStatus 要准转变到的状态
     * */
    fun changeStepStatus(toStatus: Int) {
        stateChangeAnimator.start()
        this.toStatus = toStatus
    }

    /* 判断是否是状态切换 */
    fun isStatusChange(): Boolean = this.stepStatus != this.toStatus

    /**
     * 监听回调 自身状态 自身选择状态切换监听
     */
    interface StepViewListener {

        fun onStatusChangeListener(fromStatus: Int, toStatus: Int)

        fun onSelectedStateChangeListener(isSelected: Boolean)

    }
}
