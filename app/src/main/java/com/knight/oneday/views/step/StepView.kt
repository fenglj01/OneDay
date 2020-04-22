package com.knight.oneday.views.step

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.IntDef
import com.knight.oneday.R

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
    var circleRadius: Int = 0
    @Dimension(unit = Dimension.SP)
    var stepNumberTextSize: Float = 0F
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


    init {
        initAttrs()
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
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}
