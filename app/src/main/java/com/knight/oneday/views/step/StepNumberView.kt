package com.knight.oneday.views.step

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import com.knight.oneday.R
import com.knight.oneday.utilities.*

/**
 * Create by FLJ in 2020/4/15 13:44
 * 步骤View 未完成圆环空心 中间数字 完成为 实心圆形 中间数字
 */
class StepNumberView : FrameLayout {


    private val padding: Float = dp2px(context, 8F)
    private val contentSize: Float = dp2px(context, 24F)
    private val radius: Float = dp2px(context, 12F)

    private var finishedColor: Int = 0
    private var unfinishedColor: Int = 0
    private var finishedTextColor: Int = 0
    private var unfinishedTextColor: Int = 0

    private lateinit var stepView: View
    private lateinit var stepTextView: AppCompatTextView


    var stepNumber: Int = 0
        set(value) {
            field = value
            if (::stepTextView.isInitialized)
                stepTextView.text = stepNumber.toString()
        }

    var onStepStateChangedListener: OnStepStateChangedListener? = null

    private var state: Int = STEP_STATE_FINISHED

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, def: Int) : super(
        context,
        attributeSet,
        def
    ) {
        initAttr(attributeSet)
        inflater()
        initListener()
    }

    private fun inflater() {
        stepView = LayoutInflater.from(context).inflate(R.layout.step_number_view, this, true)
        stepTextView = stepView.findViewById(R.id.step_number_tv)
        turnState()
    }


    @SuppressLint("CustomViewStyleable")
    private fun initAttr(attrs: AttributeSet?) {
        val typeArray = context!!.obtainStyledAttributes(attrs, R.styleable.CreateStepView)
        finishedColor = typeArray.getColor(
            R.styleable.CreateStepView_stepColorFinished,
            Color.BLACK
        )
        finishedTextColor = typeArray.getColor(
            R.styleable.CreateStepView_stepTextColorFinished,
            Color.BLACK
        )
        unfinishedColor = typeArray.getColor(
            R.styleable.CreateStepView_stepColorUnfinished,
            Color.WHITE
        )
        unfinishedTextColor = typeArray.getColor(
            R.styleable.CreateStepView_stepTextColorUnfinished,
            context.resources.getColor(R.color.color_on_surface_emphasis_high)
        )
        state = typeArray.getInt(
            R.styleable.CreateStepView_stepState,
            STEP_STATE_FINISHED
        )
        stepNumber = typeArray.getInt(
            R.styleable.CreateStepView_stepNumberInt,
            1
        )
        typeArray.recycle()
    }

    fun turnState() {
        state = if (state == STEP_STATE_UNFINISHED) STEP_STATE_FINISHED else STEP_STATE_UNFINISHED
        if (state == STEP_STATE_FINISHED) {
            stepView.setBackgroundResource(R.drawable.step_number_view_finished)
            stepTextView.strikeText()
        } else {
            stepView.setBackgroundResource(R.drawable.step_number_view_unfinished)
            stepTextView.clearStrikeText()
        }
    }

    private fun initListener() {
        singleClick(200L) {
            turnState()
            onStepStateChangedListener?.onStepStateChange(state)
        }
    }

    fun clearOnStepStateChangedListener() {
        onStepStateChangedListener = null
    }

    interface OnStepStateChangedListener {
        fun onStepStateChange(state: Int)
    }


}

const val STEP_STATE_UNFINISHED = 0
const val STEP_STATE_FINISHED = 1

